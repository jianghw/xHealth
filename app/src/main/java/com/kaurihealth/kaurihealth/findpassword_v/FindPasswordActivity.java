package com.kaurihealth.kaurihealth.findpassword_v;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.resetpassword_v.ResetPasswordActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.findpassword_p.FindPasswordPresenter;
import com.kaurihealth.mvplib.findpassword_p.IFindPasswordView;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.TimeCount;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 找回密码
 */
public class FindPasswordActivity extends BaseActivity implements IFindPasswordView, Validator.ValidationListener {
    //标注:提示这是需要依赖导入的对象
    @Inject
    FindPasswordPresenter<IFindPasswordView> mFindPasswordPresenter;

    //电话号码
    @Pattern(regex = "^1[3|4|5|7|8]\\d{9}$", message = "请输入正确的电话号码")
    @NotEmpty(message = "手机号码不能为空")
    @Bind(R.id.edt_phonenumber)
    EditText edtPhonenumber;
    //验证码编辑框
    @Pattern(regex = "^\\d{4}$", message = "验证码必须为4位数字")
    @NotEmpty(message = "验证码不能为空")
    @Bind(R.id.edt_verification_code)
    EditText edtVerificationCode;

    //下一步
    @Bind(R.id.tv_submit)
    Button mButton;

    //获取验证码按钮
    @Bind(R.id.tv_get_verificationcode)
    TextView tvGetVerificationcode;

    private Validator mValidator;
    private TimeCount timeCount;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_findpassword;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mFindPasswordPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn(getString(R.string.find_password));

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFindPasswordPresenter.unSubscribe();
        if (timeCount != null) timeCount.cancel();
    }

    /**
     * ---------------------点击事件----------------------
     */                                     //下一步
    @OnClick({R.id.tv_get_verificationcode, R.id.tv_submit})
    public void onClick(View view) {
        if (OnClickUtils.onNoDoubleClick()) return;
        switch (view.getId()) {
            case R.id.tv_get_verificationcode:
                //发送验证码
                mFindPasswordPresenter.sendVerificationCodeRequest();
                break;
            case R.id.tv_submit:   //下一步
                //表单验证开启
                mValidator.validate();
                break;
            default:
                break;
        }
    }

    //获取验证码开始倒计时
    @Override
    public void startCountDown() {
        if (timeCount != null) timeCount.cancel();
        timeCount = new TimeCount(60000, 1000, tvGetVerificationcode, "%d秒后重试", "获取验证码");
        timeCount.start();
    }

    @Override
    public void showPhoneNumberErrorMessage() {
        String message = getResources().getString(R.string.register_phone_pattern);
        DialogUtils.sweetWarningDialog(this, message);
    }

    @Override
    public void onValidationSucceeded() {
        mFindPasswordPresenter.onSubscribe();
    }


    //validator重写方法:表单信息验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplication(), errors);
        displayErrorDialog(message);
    }

    @Override
    public void switchToActivity(ResponseDisplayBean responseDisplayBean) {
        //放数据哪里都能放传给其他页面的数据
        Bundle bundle = new Bundle();
        bundle.putString("hashCode", responseDisplayBean.getMessage());
        bundle.putString("phoneNumber", getPhoneNumber());
        skipToBundle(ResetPasswordActivity.class, bundle);
    }

    //跳转到下一个界面...
    @Override
    public void switchPageUI(String className) {

    }

    //后添加-获取电话号码内容
    @Override
    public String getPhoneNumber() {
        return edtPhonenumber.getText().toString().trim();
    }

    //后添加-获取验证码内容
    @Override
    public String getVersionCodeContent() {
        return edtVerificationCode.getText().toString().trim();
    }
}
