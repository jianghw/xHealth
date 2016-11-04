package com.kaurihealth.kaurihealth.login_v;

import android.content.Intent;
import android.os.Bundle;
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
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.findpassword_v.FindPasswordActivity;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterPersonInfoActivity;
import com.kaurihealth.mvplib.login_p.ILoginView;
import com.kaurihealth.mvplib.login_p.LoginPresenter;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.SharedUtils;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
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
    /**
     * 标注，提示这是需要依赖导入的对象
     */
    @Inject
    LoginPresenter<ILoginView> mLoginPresenter;

    @Bind(R.id.portrait_login)
    CircleImageView mPortraitLogin;

    @Bind(R.id.edt_name)
    @NotEmpty(messageResId = R.string.login_edit_name)
    EditText mEdtName;

    @Bind(R.id.img_del_name)
    ImageView mImgDelName;

    @Bind(R.id.edt_password)
    @NotEmpty(messageResId = R.string.login_edit_pw)
    EditText mEdtPassword;

    @Bind(R.id.img_del_pw)
    ImageView mImgDelPw;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_register)
    TextView mTvRegister;

    @Bind(R.id.tv_forget_pw)
    TextView mTvForgetPw;

    private Validator mValidator;//调用.validate()

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
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
        mLoginPresenter.unSubscribe();
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

    @OnClick({R.id.btn_login, R.id.tv_register, R.id.tv_forget_pw})
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
            default:
                break;
        }
    }

    /**
     * ----------------------------View中方法----------------------------------
     **/
    @Override
    public String getUserName() {
        return mEdtName.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString().trim();
    }

    @Override
    public void setUserName(String name) {
        mEdtName.setText(name);
    }

    @Override
    public void setPassword(String password) {
        mEdtPassword.setText(password);
    }


    public void setImageHead(String url) {
        Picasso.with(this).load(url).fit().into(mPortraitLogin);
    }

    @Override
    public void completeRegister() {
        dismissInteractionDialog();
        switchPageUI(Global.Jump.RegisterPersonInfoActivity);
    }

    @Override
    public void showSuccessToast() {
        showToast(getString(R.string.login_success));
    }

    //保存用户名到SP中
    @Override
    public void saveUsername() {
        if (mEdtName != null)
            SharedUtils.setString(this, "username", mEdtName.getText().toString().trim());
    }


    @Override
    public void loginError(Throwable e) {
        mBtnLogin.setEnabled(true);
    }

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
            default:
                break;
        }
    }

    @Override
    public LoginBean createRequestBean() {
        String userName = getUserName();
        String password = getPassword();
        return new LoginBeanBuilder().Build(userName, password);
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
                    dismissInteractionDialog();
                    showSuccessToast();
                    switchPageUI(Global.Jump.MainActivity);
                }
            });
        }
    }


}
