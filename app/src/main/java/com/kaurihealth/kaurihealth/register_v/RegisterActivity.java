package com.kaurihealth.kaurihealth.register_v;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.register_p.IRegisterView;
import com.kaurihealth.mvplib.register_p.RegisterPresenter;
import com.kaurihealth.utilslib.TimeCount;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.broadcast.BroadcastFactory;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/8/10.
 */
public class RegisterActivity extends BaseActivity implements Validator.ValidationListener, IRegisterView {

    @Bind(R.id.tv_errchoose)
    TextView tvErrchoose;

    //电话
    @Pattern(regex = "^1[3|4|5|7|8]\\d{9}$", messageResId = R.string.register_phone_pattern)
    @Bind(R.id.edt_phone_number)
    EditText edtPhonenumber;
    @Bind(R.id.tv_errorphone)
    TextView tvErrorphone;

    //验证码
    @Pattern(regex = "^\\d{4}$", messageResId = R.string.register_verification_pattern)
    @Bind(R.id.edt_verification_code)
    EditText edtVerificationCode;
    @Bind(R.id.tv_get_verificationcode)
    TextView tvGetVerificationcode;
    @Bind(R.id.tv_errorversioncode)
    TextView tvErrorversioncode;

    //设置密码
    @Password(scheme = Password.Scheme.ALPHA_NUMERIC, messageResId = R.string.register_pw_password)
    @Length(min = 6, max = 20, messageResId = R.string.register_pw_length)
    @Bind(R.id.edt_password)
    EditText edtPassword;
    @Bind(R.id.tv_errorpassword)
    TextView tvErrorpassword;

    //确认密码
    @ConfirmPassword(messageResId = R.string.register_pw_confirm)
    @Bind(R.id.edt_confirmpassword)
    EditText edtConfirmpassword;

    @Bind(R.id.tv_errorconfirmpassword)
    TextView tvErrorconfirmpassword;

    @Checked(messageResId = R.string.register_checked)
    @Bind(R.id.checkbox_agree)
    CheckBox checkboxAgree;
    @Bind(R.id.tv_erroragreement)
    TextView tvErroragreement;

    @Inject
    RegisterPresenter<IRegisterView> mPresenter;

    private Validator mValidator;//调用.validate()
    private BroadcastReceiver broadcastReceiver;
    private final int agreement = 100;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.register;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = BroadcastFactory.getSmsIntentFileter();
        broadcastReceiver = BroadcastFactory.getSmsReceiver(edtVerificationCode);
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * ----------------------点击事件----------------------
     */
    @OnClick({R.id.tv_get_verificationcode, R.id.tv_register, R.id.tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verificationcode:
                //发送验证码
                mPresenter.sendVerificationCodeRequest();
                break;
            case R.id.tv_register:
                //表单验证开启
                mValidator.validate();
                break;
            case R.id.tv_agreement:
                skipToForResult(AgreementActivity.class, null, agreement);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public String getPhoneNumber() {
        return edtPhonenumber.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString().trim();
    }

    @Override
    public String getVerificationCode() {
        return edtVerificationCode.getText().toString().trim();
    }

    public void showPhoneNumberErrorMessage() {
        String message = getResources().getString(R.string.register_phone_pattern);
        DialogUtils.sweetWarningDialog(this, message);
    }

    @Override
    public void startCountDown() {
        TimeCount timeCount = new TimeCount(60000, 1000, tvGetVerificationcode, "%d秒后重新获取", "获取验证码");
        timeCount.start();
    }

    /**
     * 表单验证成功
     */
    @Override
    public void onValidationSucceeded() {
        mPresenter.onSubscribe();
    }

    /**
     * 表单验证信息失败
     *
     * @param errors
     */
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    /**
     * 跳转信息完善页面
     * @param className
     */
    @Override
    public void switchPageUI(String className) {
        skipTo(RegisterPersonInfoActivity.class);
    }


    /**
     * 界面返回信息处理
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case agreement:
                switch (resultCode) {
                    case RESULT_OK:
                        checkboxAgree.setChecked(true);
                        break;
                }
                break;
        }
    }
}
