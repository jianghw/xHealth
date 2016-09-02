package com.kaurihealth.kaurihealth.login_v;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.builder.LoginBeanBuilder;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.findpassword_v.FindPasswordActivity;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterPersonInfoActivity;
import com.kaurihealth.mvplib.login_p.ILoginView;
import com.kaurihealth.mvplib.login_p.LoginPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.CircleImageView;
import com.kaurihealth.utilslib.widget.TextWatchClearError;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements ILoginView, Validator.ValidationListener {

    private final int Register = 10;
    private final int FindPassword = 11;

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
    @Bind(R.id.layout_login)
    LinearLayout mLayoutLogin;

    private Validator mValidator;//调用.validate()

    /**
     * 标注，提示这是需要依赖导入的对象
     */
    @Inject
    LoginPresenter<ILoginView> mLoginPresenter;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unSubscribe();
    }

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mLoginPresenter.setPresenter(this);

        //判断是否登录过
        mLoginPresenter.everLogin();

        mEdtName.setText("15921947713");
        mEdtPassword.setText("123456789");
    }


    @Override
    protected void initDelayedView() {
        mEdtName.addTextChangedListener(new TextWatchClearError(mEdtName, mImgDelName));
        mEdtPassword.addTextChangedListener(new TextWatchClearError(mEdtPassword, mImgDelPw));

        //表单初始化
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
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
        switch (view.getId()) {
            case R.id.btn_login://登陆
                mValidator.validate();
                break;
            case R.id.tv_register://注册
                skipToForResult(RegisterActivity.class, null, Register);
                break;
            case R.id.tv_forget_pw://忘记密码
                skipToForResult(FindPasswordActivity.class, null, FindPassword);
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
    public void setUserName() {

    }

    @Override
    public void setPassword() {

    }

    @Override
    public void setImageHead() {

    }

    @Override
    public void loginSuccessful() {
        switchPageUI(Global.Jump.MainActivity);
    }

    @Override
    public void completeRegister() {
        switchPageUI(Global.Jump.RegisterPersonInfoActivity);
    }

    @Override
    public void showSuccessToast() {
        showToast(getString(R.string.login_success));
    }

    @Override
    public void loginError(Throwable e) {
        displayErrorDialog(e.getMessage());
    }

    @Override
    public void switchPageUI(String className) {

        switch (className) {
            case Global.Jump.RegisterPersonInfoActivity:
                skipTo(RegisterPersonInfoActivity.class);
                break;
            case Global.Jump.MainActivity:
                skipTo(MainActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public <T> T createRequestBean() {
        String userName = getUserName();
        String password = getPassword();
        return (T) new LoginBeanBuilder().Build(userName, password);
    }

}
