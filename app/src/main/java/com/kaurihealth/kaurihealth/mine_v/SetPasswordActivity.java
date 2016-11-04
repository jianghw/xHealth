package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.widget.EditText;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewPasswordBeanBuilder;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.ISetPasswordView;
import com.kaurihealth.mvplib.mine_p.SetPasswordPresenter;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Garnet_Wu on 2016/8/24.
 *
 * 描述: 我的--> 设置--> 修改密码
 */
public class SetPasswordActivity extends BaseActivity  implements ISetPasswordView,Validator.ValidationListener {
    @Inject
    SetPasswordPresenter<ISetPasswordView> mSetPasswordPresenter;

    //原密码
    @NotEmpty(message = "原密码不能为空")
    @Bind(R.id.edtOriginalPassWord)
    EditText edtOriginalPassWord;

    //新密码
    @Pattern(regex ="^[^\\u4e00-\\u9fa5]{0,}$",messageResId = R.string.register_pw_password)   //里面不能包含中文正则
    @Password(messageResId = R.string.register_pw_password)
    @Length(max = 20, message = "为了保证您的账户安全，密码长度须为6到20位")
    @Bind(R.id.edtNewPassword)
    EditText edtNewPassword;
    //确认密码
    @ConfirmPassword(message = "密码不一致")
    @Bind(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    private Validator validator;

    //BaseActivity
    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_setpassword;
    }

    //BaseActivity
    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mSetPasswordPresenter.setPresenter(this);
    }

    //BaseActivity
    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);  //回退键
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    //IMvpView  跳转页面--> 跳转至Setting界面
    @Override
    public void switchPageUI(String className) {
        //我的-->设置--> 修改密码  修改密码之后返回设置界面
        finishCur();
    }

    @OnClick(R.id.btn_Submit)
    public  void  onClick(){
       validator.validate();  //开启表单验证
    }

    //表单验证成功
    @Override
    public void onValidationSucceeded() {
        //网络请求数据
        mSetPasswordPresenter.onSubscribe();


    }

    //表单验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        //弹出sweetDialog对话框
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }


    //得到修改密码的bean类
    @Override
    public NewPasswordDisplayBean getNewPasswordDisplayBean() {
        //用户名
        String userName = LocalData.getLocalData().getTokenBean().getUser().getUserName();
        // 旧密码
        String existPassword = edtOriginalPassWord.getText().toString().trim();
        //新密码
        String newPassword = edtNewPassword.getText().toString().trim();
        NewPasswordDisplayBean newPasswordDisplayBean = new NewPasswordBeanBuilder().Build(userName, newPassword, existPassword);
        return newPasswordDisplayBean;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSetPasswordPresenter.unSubscribe();
    }

}
