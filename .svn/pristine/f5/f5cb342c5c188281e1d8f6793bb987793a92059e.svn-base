package com.kaurihealth.kaurihealth.resetpassword_v;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.ResetPasswordDisplayBeanBuilder;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.login_v.LoginActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.resetpassword_p.IResetPasswordView;
import com.kaurihealth.mvplib.resetpassword_p.ResetpasswordPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
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
 * Created by Garnet_Wu on 2016/8/18.
 * 说明： 重置密码 ：忘记密码下一界面
 */
public class ResetPasswordActivity extends BaseActivity implements IResetPasswordView, Validator.ValidationListener {
    @Inject
    ResetpasswordPresenter<IResetPasswordView> mResetpasswordPresenter;

    //新密码
    //新密码
    @Pattern(regex = "^[^\\u4e00-\\u9fa5]{0,}$", messageResId = R.string.register_pw_password)
    //里面不能包含中文正则
    @Password(messageResId = R.string.register_pw_password)
    @Length(max = 20, message = "为了保证您的账户安全，密码长度须为6到20位")
    @Bind(R.id.edt_password)
    EditText edtPassword;

    //确认密码
    @ConfirmPassword(message = "两次密码不一致")
    @Bind(R.id.edt_confirmpassword)
    EditText edtConfirmpassword;

    @Bind(R.id.tv_more)
    TextView tvMore;

    private Validator validator;
    private Bundle bundle;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_resetpassword;
    }

    //BaseActivity  初始化presenter指挥家和数据
    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mResetpasswordPresenter.setPresenter(this);
        //拿上个页面的bundle
        bundle = getIntent().getExtras();
    }

    //BaseActivity
    @Override
    protected void initDelayedData() {   //初始化表单校验器
        initNewBackBtn(getString(R.string.set_password));
        tvMore.setText(getString(R.string.title_complete));

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mResetpasswordPresenter.unSubscribe();
    }

    //"完成"按钮
    @OnClick(R.id.tv_more)
    public void tv_submit() {
        //请求表单验证
        validator.validate();
    }

    //Validator 表单验证通过
    @Override
    public void onValidationSucceeded() {
        //presenter 点击“完成”按钮
        mResetpasswordPresenter.onSubscribe();
    }

    //Validator 表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        //弹出sweetDialog对话框
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }

    @Override
    public ResetPasswordDisplayBean getResetPasswordDisplayBean() {
        String newPassword = edtPassword.getText().toString().trim();
        String hashCode = bundle.getString("hashCode"); //200 ？ 看下是不是验证码
        String phoneNumber = bundle.getString("phoneNumber"); //13861021881 已拿到
        return new ResetPasswordDisplayBeanBuilder().Build(phoneNumber, newPassword, hashCode);
    }

    @Override
    public void switchPageUI(String className) {
        //重置密码,重新返回登录界面
        skipTo(LoginActivity.class);
    }

}
