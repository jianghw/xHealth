package com.kaurihealth.kaurihealth.register_v;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.NewRegisterBean;
import com.kaurihealth.datalib.request_bean.builder.NewRegisterBeanBuilder;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.register_p.IRegisterView;
import com.kaurihealth.mvplib.register_p.RegisterPresenter;
import com.kaurihealth.utilslib.TimeCount;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements Validator.ValidationListener, IRegisterView {

    @Inject
    RegisterPresenter<IRegisterView> mPresenter;

    //电话
    @Pattern(regex = "^1[3|4|5|7|8]\\d{9}$", messageResId = R.string.register_phone_pattern)
    @Bind(R.id.edt_phone_number)
    EditText edtPhoneNumber;

    //验证码
    @Pattern(regex = "^\\d{4}$", messageResId = R.string.register_verification_pattern)
    @Bind(R.id.edt_verification_code)
    EditText edtVerificationCode;

    //"获取验证码"按钮
    @Bind(R.id.tv_get_verificationcode)
    TextView tvGetVerificationCode;

    //设置密码
    @Pattern(regex = "^[^\\u4e00-\\u9fa5]{0,}$", messageResId = R.string.register_pw_password)
    //里面不能包含中文正则
    @Password(messageResId = R.string.register_pw_password)
    @Length(min = 6, max = 20, messageResId = R.string.register_pw_length)
    @Bind(R.id.edt_password)
    EditText edtPassword;

    //确认密码
    @ConfirmPassword(messageResId = R.string.register_pw_confirm)
    @Bind(R.id.edt_confirmpassword)
    EditText edtConfirmPassword;

    @Bind(R.id.tv_line_name)
    TextView mTvLineName;
    @Bind(R.id.tv_line_code)
    TextView mTvLineCode;
    @Bind(R.id.tv_line_password)
    TextView mTvLinePassword;
    @Bind(R.id.tv_line_confirmpassword)
    TextView mTvLineConfirmpassword;

    private Validator mValidator;
    //    private BroadcastReceiver broadcastReceiver;

    private TimeCount timeCount;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activiyt_register;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        findViewById(R.id.iv_back).setOnClickListener(view -> finishCur());
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        setListener(edtPhoneNumber, mTvLineName, R.mipmap.phone_icon, R.mipmap.phone_icon_on);
        setListener(edtVerificationCode, mTvLineCode, R.mipmap.code_icon, R.mipmap.code_icon_on);
        setListener(edtPassword, mTvLinePassword, R.mipmap.password_icon, R.mipmap.password_icon_on);
        setListener(edtConfirmPassword, mTvLineConfirmpassword, R.mipmap.password_icon, R.mipmap.password_icon_on);
    }

    /**
     * 控件设置监听
     */
    private void setListener(EditText editText, TextView tvLinePw, int greyIcon, int greenIcon) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    changeColorByFocus(greenIcon, editText, tvLinePw,true);
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

    @Override
    protected void onStart() {
        super.onStart();
//        IntentFilter intentFilter = BroadcastFactory.getSmsIntentFileter();
//        broadcastReceiver = BroadcastFactory.getSmsReceiver(edtVerificationCode);
//        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        this.unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) timeCount.cancel();
        mPresenter.unSubscribe();
    }

    /**
     * ----------------------点击事件----------------------
     */
    @OnClick({R.id.tv_get_verificationcode, R.id.tv_register, R.id.tv_agreement})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verificationcode://发送验证码
                mPresenter.sendVerificationCodeRequest();
                break;
            case R.id.tv_register:
                mValidator.validate();
                break;
            case R.id.tv_agreement:
                skipTo(AgreementActivity.class);
                break;
            default:
                break;
        }
    }

    public void showPhoneNumberErrorMessage() {
        String message = getResources().getString(R.string.register_phone_pattern);
        DialogUtils.sweetWarningDialog(this, message);
    }

    @Override
    public void startCountDown() {
        if (timeCount != null) timeCount.cancel();
        timeCount = new TimeCount(60000, 1000, tvGetVerificationCode, "%d秒后重试", "获取验证码");
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
     */
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    /**
     * 跳转信息完善页
     */
    @Override
    public void switchPageUI(String className) {
        skipTo(RegisterPersonInfoActivity.class);
    }

    @Override
    public NewRegisterBean getNewRegisterBean() {
        String phoneNumber = getPhoneNumber();
        String passWord = getPassword();
        String verificationCode = getVerificationCode();
        return new NewRegisterBeanBuilder().Build(phoneNumber, passWord, verificationCode);
    }

    @Override
    public String getPhoneNumber() {
        return edtPhoneNumber.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return edtPassword.getText().toString().trim();
    }

    @Override
    public String getVerificationCode() {
        return edtVerificationCode.getText().toString().trim();
    }

    @Override
    public void manualFinishCurrent() {
        finishCur();
    }

}
