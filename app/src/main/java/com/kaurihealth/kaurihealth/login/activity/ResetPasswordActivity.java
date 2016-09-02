//package com.kaurihealth.kaurihealth.login.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.text.TextUtils;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
//import com.kaurihealth.datalib.request_bean.bean.ResponseDisplayBean;
//import com.kaurihealth.datalib.service.IChangePasswordService;
//import com.kaurihealth.kaurihealth.R;
//import com.kaurihealth.datalib.request_bean.builder.ResetPasswordDisplayBeanBuilder;
//import com.kaurihealth.kaurihealth.services.ServiceFactory;
//import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
//import com.kaurihealth.kaurihealth.util.ExitApplication;
//import com.kaurihealth.kaurihealth.util.Url;
//import com.mobsandgeeks.saripaar.ValidationError;
//import com.mobsandgeeks.saripaar.Validator;
//import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
//import com.mobsandgeeks.saripaar.annotation.Length;
//import com.mobsandgeeks.saripaar.annotation.Password;
//import com.youyou.zllibrary.util.CommonActivity;
//
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import retrofit2.Call;
//import retrofit2.Callback;
//
///**
// * 版权:    张磊
// * 作者:    张磊
// * 描述：
// * 修订日期:
// */
//public class ResetPasswordActivity extends CommonActivity implements Validator.ValidationListener{
//    @Password(message = "为了保证您的账户安全，密码长度须为6到20位")
//    @Length(max = 20, message = "为了保证您的账户安全，密码长度须为6到20位")
//    @Bind(R.id.edt_password)
//    EditText edtPassword;
//
//    @Bind(R.id.tv_errorpassword)
//    TextView tvErrorpassword;
//
//    @ConfirmPassword(message = "密码不一致")
//    @Bind(R.id.edt_resetpassword)
//    EditText edtResetpassword;
//
//    @Bind(R.id.tv_errorconfirmpassword)
//    TextView tvErrorconfirmpassword;
//
//    @Bind(R.id.tv_errorsubmit)
//    TextView tvErrorsubmit;
//    private String hashCode;
//    private String phoneNumber;
//    private IChangePasswordService changePasswordService;
//    private Handler handler;
//    private Validator validator;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.resetpassword);
//        ButterKnife.bind(this);
//        init();
//        ExitApplication.getInstance().addActivity(this);
//    }
//
//    @Override
//    public void init() {
//        super.init();
//        setBack(R.id.iv_back);
//        hashCode = getBundle().getString("hashCode");
//        phoneNumber = getBundle().getString("phoneNumber");
//        if (TextUtils.isEmpty(hashCode) || TextUtils.isEmpty(phoneNumber) || (!phoneNumber.matches("\\d{11,}"))) {
//            finishCur();
//        }
//
//
//        handler = new Handler();
//
//        //retrofit初始化
//        changePasswordService = new ServiceFactory(Url.prefix,getApplicationContext()).getChangePasswordService();
//        validator = new Validator(this);
//        validator.setValidationListener(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//    }
//
//    //提交
//    @OnClick(R.id.tv_submit)
//    public void tv_submit() {
//        validator.validate();
//    }
//
//
//
//    @Override
//    public void onValidationSucceeded() {
//        final String passStr = edtPassword.getText().toString().trim();
//        ResetPasswordDisplayBean resetPasswordDisplayBean = new ResetPasswordDisplayBeanBuilder().Build( phoneNumber, passStr, hashCode);
//        Call<ResponseDisplayBean> call = changePasswordService.ResetUserPassword_out(resetPasswordDisplayBean);
//        call.enqueue(new Callback<ResponseDisplayBean>() {
//            @Override
//            public void onResponse(Call<ResponseDisplayBean> call, retrofit2.Response<ResponseDisplayBean> response) {
//                if (response.isSuccessful() && response.body().isSucess) {
//                    showToast("修改密码成功");
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            Intent intent = getIntent();
//                            intent.putExtra("phone", phoneNumber);
//                            intent.putExtra("pass", passStr);
//                            setResult(RESULT_OK, intent);
//                            finishCur();
//                        }
//                    }, 1000);
//                } else {
//                    showToast("修改密码失败" + response.code());  //提示用户错误码
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
//                showToast(LoadingStatu.NetError.value);
//            }
//        });
//
//
//
//    }
//
//    @Override
//    public void onValidationFailed(List<ValidationError> errors) {
//        showValidationMessage(errors);
//    }
//}
