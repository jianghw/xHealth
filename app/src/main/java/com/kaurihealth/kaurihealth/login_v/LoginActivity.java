package com.kaurihealth.kaurihealth.login_v;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.request_bean.builder.LoginBeanBuilder;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.findpassword_v.FindPasswordActivity;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterPersonInfoActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.login_p.ILoginView;
import com.kaurihealth.mvplib.login_p.LoginPresenter;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.SharedUtils;
import com.kaurihealth.utilslib.TimeCount;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;
import com.kaurihealth.utilslib.widget.TextWatchClearError;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView, Validator.ValidationListener {
    @Inject
    LoginPresenter<ILoginView> mLoginPresenter;

    @Bind(R.id.portrait_login)
    CircleImageView mPortraitLogin;

    //手机号
    @Bind(R.id.edt_name)
    @NotEmpty(message = "手机号不能为空")
    EditText mEdtName;

    @Bind(R.id.img_del_name)
    ImageView mImgDelName;

    //密码编辑框
    @Bind(R.id.edt_password)
    @NotEmpty(message ="密码不能为空")
    EditText mEdtPassword;

    //验证码编辑框
    @Bind(R.id.edt_verification)
    @NotEmpty(message = "验证码不能为空")
    EditText  mEdtVerifyCode;

    //删除键
    @Bind(R.id.img_del_pw)
    ImageView mImgDelPw;

    //"登录"按钮
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    //"立即注册"按钮
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    //忘记密码
    @Bind(R.id.tv_forget_pw)
    TextView mTvForgetPw;

    //"验证码登录"提示语
    @Bind(R.id.tv_verify)
    TextView mTvVerify;

    //"或"字提示
    @Bind(R.id.tv_select)
    TextView mTvSelect;

    //"发送验证码"按钮
    @Bind(R.id.tv_get_verificationcode)
    TextView tvGetVerificationCode;


    @Bind(R.id.tv_line_name)
    TextView mTvLineName;
    @Bind(R.id.tv_line_pw)
    TextView mTvLinePw;

    private Validator mValidator;
    private TimeCount timeCount;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mLoginPresenter.setPresenter(this);
        //取出数据进行回显
        mEdtName.setText(SharedUtils.getString(this, "username", ""));
    }

    @Override
    protected void initDelayedData() {
        mEdtName.addTextChangedListener(new TextWatchClearError(mEdtName, mImgDelName));
        mEdtPassword.addTextChangedListener(new TextWatchClearError(mEdtPassword, mImgDelPw));

        //表单初始化
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        // 忘记密码出现, 获取验证码按钮隐藏 提示语:验证码登录
        initTypeLogin(true);

        setListener(mEdtName, mTvLineName, R.mipmap.phone_icon, R.mipmap.phone_icon_on);
        setListener(mEdtPassword, mTvLinePw, R.mipmap.password_icon, R.mipmap.password_icon_on);


        //验证码编辑框
        setListener(mEdtVerifyCode,mTvLinePw,R.mipmap.code_icon,R.mipmap.code_icon_on);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mEdtName != null) mEdtName.setText(SharedUtils.getString(this, "username", ""));
        if (mEdtPassword != null) mEdtPassword.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) timeCount.cancel();
        mLoginPresenter.unSubscribe();
    }

    /**
     * 编辑框设置监听
     */
    private void setListener(EditText editText, TextView tvLinePw, int greyIcon, int greenIcon) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    changeColorByFocus(greenIcon, editText, tvLinePw, true);
                } else if (editText.getText().toString().length() > 0) {
                    changeColorByFocus(greenIcon, editText, tvLinePw, true);
                } else {
                    changeColorByFocus(greyIcon, editText, tvLinePw, false);
                }
            }
        });
    }

    private void changeColorByFocus(int greenIcon, EditText editText, TextView tvLinePw, boolean isFocus) {
        Drawable drawableLeft = getGreenDrawable(greenIcon);
        editText.setCompoundDrawables(drawableLeft, null, null, null);
        tvLinePw.setBackgroundResource(isFocus ? R.color.color_enable_green : R.color.color_text_gray);
    }

    @NonNull
    private Drawable getGreenDrawable(int greenIcon) {
        Drawable drawableLeft = getResources().getDrawable(greenIcon);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        return drawableLeft;
    }

    /**
     * ----------------------------表单验证回调方法----------------------------------
     **/
    @Override
    public void onValidationSucceeded() {
        mLoginPresenter.onSubscribe();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    /**
     * 点击
     */
    @OnClick({R.id.tv_verify})
    public void clickVerify(View view) {
        typeToLogin();
    }

    private void typeToLogin() {
        boolean isPassWord = isPassWordLogin();
        initTypeLogin(isPassWord);
    }

    // 忘记密码出现, 获取验证码按钮隐藏 提示语:验证码登录
    private void initTypeLogin(boolean isPassWord) {
        tvGetVerificationCode.setVisibility(isPassWord ? View.GONE : View.VISIBLE);
        mTvForgetPw.setVisibility(isPassWord ? View.VISIBLE : View.GONE);
        mTvSelect.setText(isPassWord ? "或" : "返回");
        mTvVerify.setText(isPassWord ? "验证码登录" : "密码登录");

        //验证码编辑框-->与获取"验证码"按钮的出现的规律一致
        mEdtVerifyCode.setVisibility(isPassWord ? View.GONE : View.VISIBLE);

        //密码编辑框 --> 与"忘记密码"出现的规律一致
        mEdtPassword.setVisibility(isPassWord ? View.VISIBLE :View.GONE);

    }

    /**
     * 是否密码登陆
     */
    private boolean isPassWordLogin() {
        return mTvVerify.getText().toString().trim().contains("密码");
    }

    /**
     * ---------------------点击事件----------------------
     */
    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget_pw, R.id.tv_get_verificationcode})
    public void onClick(View view) {
        if (OnClickUtils.onNoDoubleClick()) return;
        switch (view.getId()) {
            case R.id.btn_login://登陆
                mValidator.validate();
                break;
            case R.id.tv_register://注册
                switchPageUI(Global.Jump.RegisterActivity);
                break;
            case R.id.tv_forget_pw://忘记密码
                switchPageUI(Global.Jump.FindPasswordActivity);
                break;
            case R.id.tv_get_verificationcode://发送验证码 P层参与
                mLoginPresenter.sendVerificationCodeRequest();
                break;
            default:
                break;
        }
    }

    /**
     * ----------------------------View中方法----------------------------------
     **/
    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.RegisterActivity:
                skipTo(RegisterActivity.class);
                break;
            case Global.Jump.RegisterPersonInfoActivity:
                skipTo(RegisterPersonInfoActivity.class);
                break;
            case Global.Jump.MainActivity:
                skipTo(MainActivity.class);
                break;
            case Global.Jump.FindPasswordActivity:
                skipTo(FindPasswordActivity.class);
                break;
            case Global.Jump.MineFragment:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra(Global.Environment.BUNDLE, 4);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public String getUserName() {
        return mEdtName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString().trim();
    }

    public void setImageHead(String url) {
        Picasso.with(this).load(url).fit().into(mPortraitLogin);
    }

    @Override
    public void completeRegister() {
        switchPageUI(Global.Jump.RegisterPersonInfoActivity);
    }

    @Override
    public void showSuccessToast() {
        showToast(getString(R.string.login_success));
    }

    //保存用户名到SP中
    @Override
    public void saveUsername() {
        if (mEdtName != null) {
            SharedUtils.setString(this, "username", mEdtName.getText().toString().trim());
        }
    }

    @Override
    public void loginError(Throwable e) {
        mBtnLogin.setEnabled(true);
    }

    @Override
    public LoginBean createRequestBean() {
        String userName = getUserName();
        String password = getPassword();
        LoginBean loginBean = new LoginBeanBuilder().Build(userName, password);
        if (isPassWordLogin()) loginBean.setLoginType("1");//验证码登录请填写"1"
        return loginBean;
    }

    @Override
    public void setLoginBtnEnable(boolean flag) {
        mBtnLogin.setEnabled(flag);
    }

    @Override
    public void initChatKitOpen(TokenBean tokenBean) {
        UserBean userBean = tokenBean.getUser();
        if (userBean != null) {
            LCChatKit.getInstance().open(userBean.getKauriHealthId(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (e != null) showToast("聊天通讯未连接上,请重新登录" + e.getMessage());
                    showSuccessToast();
                    switchPageUI(Global.Jump.MainActivity);
                }
            });
        }
    }

    //提示输入电话号码有误
    @Override
    public void showPhoneNumberErrorMessage() {
        String message = "请输入正确的电话号码";
        DialogUtils.sweetWarningDialog(this, message);
    }

    //发送验证码之后开始倒计时
    @Override
    public void startCountDown() {
        if (timeCount != null) timeCount.cancel();
        timeCount = new TimeCount(60000, 1000, tvGetVerificationCode, "%d秒后重试", "获取验证码");
        timeCount.start();
    }

    @Override
    public void unauthorizedPersonnel(TokenBean tokenBean) {
        UserBean userBean = tokenBean.getUser();
        if (userBean != null) {
            LCChatKit.getInstance().open(userBean.getKauriHealthId(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (e != null) showToast("聊天通讯未连接上,请重新登录" + e.getMessage());
                    showSuccessToast();
                    switchPageUI(Global.Jump.MineFragment);
                }
            });
        }
    }
}
