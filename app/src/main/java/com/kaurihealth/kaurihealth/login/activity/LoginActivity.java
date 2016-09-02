package com.kaurihealth.kaurihealth.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.chatlibrary.chat.ChatInjection;
import com.example.commonlibrary.widget.CircleImageView;
import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.utilslib.bugtag.BugTagUtils;
import com.kaurihealth.utilslib.bugtag.EnvironmentConfig;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.ErrorMesBean;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.request_bean.builder.LoginBeanBuilder;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.kaurihealth.BuildConfig;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.activity.MainActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterPersonInfoActivity;
import com.kaurihealth.kaurihealth.services.AuthenticationService;
import com.kaurihealth.kaurihealth.services.IAuthenticationService;
import com.kaurihealth.kaurihealth.services.RetrofitUtil;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.kaurihealth.kaurihealth.util.PicassoImageLoadUtil;
import com.kaurihealth.kaurihealth.util.Putter;
import com.kaurihealth.kaurihealth.util.TextWatchClearChange;
import com.kaurihealth.kaurihealth.util.TextWatchClearError;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.SpUtil;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class LoginActivity extends CommonActivity implements Validator.ValidationListener {

    @Bind(R.id.portrait_login)
    CircleImageView portraitLogin;
    @Bind(R.id.edt_name_login)
    @NotEmpty(message = "请输入用户名")
    EditText edtNameLogin;
    @Bind(R.id.edt_password_login)
    @NotEmpty(message = "请输入密码")
    EditText edtPasswordLogin;
    @Bind(R.id.delete_username)
    ImageView delete_username;
    @Bind(R.id.delete_password)
    ImageView delete_password;
    private LoadingUtil loadingUtil;
    private String avatar;
    private String account;
    private final int Register = 10;
    public static final String phone = "PHONE";
    public static final String pass = "PAASS";
    public static final int ForgetPass = 11;
    private ILoginService loginService;
    private IPutter putter;
    private IGetter getter;
    private IAuthenticationService authenticationService;
    private Converter<ResponseBody, ErrorMesBean> errorConverter;
    private Validator validator;
    private Call<TokenBean> call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        init();
    }


    @Override
    public void init() {
        clearPass();
        portraitLogin.setImageResource(R.mipmap.ic_login_head_default);
        putter = new Putter(getApplicationContext());
        getter = Getter.getInstance(getApplicationContext());
        if (!(TextUtils.isEmpty((account = getter.getAccount())))) {
            edtNameLogin.setText(account);
        }
        if (!(TextUtils.isEmpty((avatar = getter.getAvatar())))) {
            PicassoImageLoadUtil.loadUrlToImageView(avatar, portraitLogin, getApplicationContext());
        }
        if (edtNameLogin.getText()==null){
            portraitLogin.setImageResource(R.mipmap.ic_login_head_default);
    }
        edtNameLogin.addTextChangedListener(new TextWatchClearError(edtNameLogin,delete_username));
        edtPasswordLogin.addTextChangedListener(new TextWatchClearError(edtPasswordLogin,delete_password));
        //随用户名输入更改头像
        edtNameLogin.addTextChangedListener(new TextWatchClearChange(edtNameLogin,portraitLogin,getter,LoginActivity.this));
        this.authenticationService = new AuthenticationService();
        this.loginService = authenticationService.getLoginService();
        this.errorConverter = RetrofitUtil.getErrorConverter(authenticationService.getRetrofit());
        this.validator = new Validator(this);
        this.validator.setValidationListener(this);
    }


    @OnClick({R.id.btn_login_login, R.id.tv_register_login, R.id.tv_forgetpassword_login,R.id.delete_username,R.id.delete_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_login:
                //登陆
                this.validator.validate();
                break;
            case R.id.tv_register_login:
                //注册
                skipToForResult(RegisterActivity.class, null, Register);
                break;
            case R.id.tv_forgetpassword_login:
                //忘记密码
                forgetPassword();
                break;
            case R.id.delete_username:
                edtNameLogin.setText("");
                break;
            case R.id.delete_password:
                edtPasswordLogin.setText("");
                break;
        }
    }

    /**
     * 忘记密码
     */
    public void forgetPassword() {
        Bundle bundle = new Bundle();
        bundle.putString("phonenumber", edtNameLogin.getText().toString());
        skipToForResult(FindpasswordActivity.class, bundle, ForgetPass);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Register:
                switch (resultCode) {
                    case RESULT_OK:
                        if (data != null) {
                            String phoneStr = data.getStringExtra(phone);
                            edtNameLogin.setText(phoneStr);
                            String passStr = data.getStringExtra(pass);
                            edtPasswordLogin.setText(passStr);
                        }
                        break;
                }
                break;
            case ForgetPass:
                if (RESULT_OK == resultCode) {
                    if (data != null) {
                        String phone = data.getStringExtra("phone");
                        String pass = data.getStringExtra("pass");
                        edtNameLogin.setText(phone);
                        edtPasswordLogin.setText(pass);
                    }
                }
                break;
        }
    }


    private void Login(LoginBean loginBean) {
        loadingUtil = LoadingUtil.getInstance(this);
        if (BuildConfig.Environment.equals("Develop")) {
            call = null;
        } else {
            call = null;
        }
        call.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                if (response.isSuccessful()) {
                    final TokenBean tokenBean = response.body();
                    if (tokenBean.user.userType.equals("医生")) {
                        putter.setTokenBean(tokenBean);
                        //设置bugtag的用户数据
                        BugTagUtils.setUserData(Config.BugTagUserKey, tokenBean.user.userName);
                        BugTagUtils.setUserData(Config.BugTagPlatform, EnvironmentConfig.CurVersion.toString());
                        BugTagUtils.setUserData(Config.BugTagUserPassKey, edtPasswordLogin.getText().toString());

                        loadingUtil.dismiss("登陆成功", new LoadingUtil.Success() {
                            @Override
                            public void dismiss() {
                                ChatInjection.chatConnect(tokenBean.user.kauriHealthId, new AVIMClientCallback() {
                                    @Override
                                    public void done(AVIMClient avimClient, AVIMException e) {
                                        if (e != null) {
                                            showToast("连接留言服务器失败");
                                        } else {
                                            ChatInjection.avimClient = avimClient;
                                            showToast("连接留言服务器成功");
                                        }
                                    }
                                });
                                SpUtil spUtil = SpUtil.getInstance(getApplicationContext());
                                spUtil.put("Auto", true);
                                //如果完成度小于30完成注册
                                if (tokenBean.user.registPercentage < 30) {
                                    skipTo(RegisterPersonInfoActivity.class);
                                } else {
                                    skipTo(MainActivity.class);
                                    finish();
                                }

                            }
                        });
                    } else {
                        loadingUtil.dismiss("该账户为患者账号，不可以登陆");
                    }
                } else if (response.code() == 500) {
                    loadingUtil.dismiss("服务器问题");
                } else {
                    try {
                        ErrorMesBean errorMesBean = errorConverter.convert(response.errorBody());
                        loadingUtil.dismiss(errorMesBean.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
//                        Bugtags.sendException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {
                loadingUtil.dismiss(LoadingStatu.NetError.value);
                t.printStackTrace();
            }
        });
       loadingUtil.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 当当前版本是preview或者debug的时候，清除密码
     */
    private void clearPass() {
        if (!BuildConfig.Environment.equals("Develop")) {
            edtPasswordLogin.setText("");
        }
    }


    @Override
    public void onValidationSucceeded() {
        String userName = edtNameLogin.getText().toString();
        String password = edtPasswordLogin.getText().toString();
        LoginBean loginBean = new LoginBeanBuilder().Build(userName, password);
        Login(loginBean);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        showValidationMessage(errors);
    }
}
