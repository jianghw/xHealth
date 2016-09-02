package com.kaurihealth.kaurihealth.mine.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewPasswordBeanBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IChangePasswordService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Url;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：  设置里修改密码界面
 * 修订日期:
 */
public class SetPassWordActivity extends CommonActivity implements Validator.ValidationListener {

    //原密码
    @NotEmpty(message = "原密码不能为空")
    @Bind(R.id.edtOriginalPassWord)
    EditText edtOriginalPassWord;

    //新密码
    @Password(message = "为了保证您的账户安全，密码长度须为6到20位")
    @Length(max = 20, message = "为了保证您的账户安全，密码长度须为6到20位")
    @Bind(R.id.edtNewPassword)
    EditText edtNewPassword;

    //确认密码
    @ConfirmPassword(message = "密码不一致")
    @Bind(R.id.edtConfirmPassword)
    EditText edtConfirmPassword;
    private Validator validator;
    private IGetter getter;
    private IChangePasswordService changePasswordService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void init() {
        super.init();
        getter = Getter.getInstance(this);
        setBack(R.id.iv_back);
        //初始化表单验证器
        validator = new Validator(this);
        validator.setValidationListener(this);
        changePasswordService = new ServiceFactory(Url.prefix,getApplicationContext()).getChangePasswordService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //提交按钮
    @OnClick(R.id.btn_Submit)
    public void onClick() {

        validator.validate();

    }

    //表单信息验证成功
    @Override
    public void onValidationSucceeded() {
//        edtOriginalPassWord.getText().toString().trim();
        //原密码
        String userName = getter.getAccount();

        String existPassword= edtOriginalPassWord.getText().toString().trim();
        //新密码
        String detnp = edtNewPassword.getText().toString().trim();

        NewPasswordDisplayBean newPasswordDisplayBean = new NewPasswordBeanBuilder().Build(userName, detnp, existPassword);

        Call<ResponseDisplayBean> call = changePasswordService.UpdateUserPassword_out(newPasswordDisplayBean);
        call.enqueue(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                if (response.isSuccessful()) {
                    if (response.body().isIsSucess()) {
                        edtOriginalPassWord.setText("");
                        edtNewPassword.setText("");
                        edtConfirmPassword.setText("");
                        new SweetAlertDialog(SetPassWordActivity.this,SweetAlertDialog.SUCCESS_TYPE).setTitleText("修改密码成功！").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finishCur();
                            }
                        }).show();
                    } else {
                        new SweetAlertDialog(SetPassWordActivity.this,SweetAlertDialog.WARNING_TYPE).setTitleText("原密码错误").show();
                       // showToast(response.body().message);
                    }
                } else {
                    showToast("修改密码失败，错误码为：" + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                showToast(LoadingStatu.NetError.value);
            }
        });

    }

    //表单信息验证失败
    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        showValidationMessage(errors);
    }
}
