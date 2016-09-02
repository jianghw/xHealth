package com.kaurihealth.kaurihealth.findpassword_v;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.resetpassword_v.ResetPasswordActivity;
import com.kaurihealth.mvplib.findpassword_p.FindPasswordPresenter;
import com.kaurihealth.mvplib.findpassword_p.IFindPasswordView;
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
 * Created by Garnet_Wu on 2016/8/15.
 */
public class FindPasswordActivity extends BaseActivity implements IFindPasswordView,Validator.ValidationListener {
    //电话号码
    @Pattern(regex = "^1[3|4|5|7|8]\\d{9}$", message = "请输入正确的电话号码")
    @NotEmpty(message = "电话号码不能为空")
    @Bind(R.id.edt_phonenumber)
    EditText edtPhonenumber;
    @Bind(R.id.tv_errorphone)
    TextView tvErrorphone;

    //验证码编辑框
    @Pattern(regex = "^\\d{4}$", message ="验证码必须为4位数字")
    @NotEmpty(message = "验证码不能为空")
    @Bind(R.id.edt_verification_code)
    EditText edtVerificationCode;
    //获取验证码按钮
    @Bind(R.id.tv_get_verificationcode)
    TextView tvGetVerificationcode;

    //错误信息提示
    @Bind(R.id.tv_errorversioncode)
    TextView tvErrorversioncode;

    //标注:提示这是需要依赖导入的对象
    @Inject
    FindPasswordPresenter<IFindPasswordView> mFindPasswordPresenter;
    private Validator mValidator;
    private  final int resetPass = 12;



    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_findpassword;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mFindPasswordPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }




    //获取验证码开始倒计时
    @Override
    public void startCountDown() {
        //TODO
        TimeCount timeCount = new TimeCount(60000, 1000, tvGetVerificationcode, "%d秒后重新获取", "获取验证码");
        timeCount.start();
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

    @Override  //？？这个不是多余的么？
    public void showPhoneNumberErrorMessage() {
        String message = getResources().getString(R.string.register_phone_pattern);
        DialogUtils.sweetWarningDialog(this, message);
    }

    //提示验证码错误的信息
    @Override
    public void showVerificationCodeError(Throwable throwable) {
        displayErrorDialog("验证码输入错误,请重新输入");
    }

    @Override
    public void switchToActivity(ResponseDisplayBean responseDisplayBean) {
        //放数据哪里都能放传给其他页面的数据
        Bundle bundle = new Bundle();
        bundle.putString("hashCode",responseDisplayBean.getMessage());
        bundle.putString("phoneNumber",getPhoneNumber());
        //TODO
        skipToForResult(ResetPasswordActivity.class, bundle, resetPass);
    }


    @Override
    public void onValidationSucceeded() {
        //点击按钮"下一步"按钮, 进入下面一个界面
        mFindPasswordPresenter.onSubscribe();

    }

    //validator重写方法:表单信息验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplication(), errors);
        displayErrorDialog(message);
    }

    //跳转到下一个界面...
    @Override
    public void switchPageUI(String className) {
        skipToForResult(ResetPasswordActivity.class, null, resetPass);
    }

    /**
     *
     *---------------------点击事件----------------------
     */                                     //下一步
    @OnClick({R.id.tv_get_verificationcode, R.id.tv_submit,R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_get_verificationcode:
                //发送验证码
                mFindPasswordPresenter.sendVerificationCodeRequest();
                break;
            case R.id.tv_submit:   //下一步
                //表单验证开启
                mValidator.validate();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
