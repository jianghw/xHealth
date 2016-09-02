package com.kaurihealth.kaurihealth.login.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayDto;
import com.kaurihealth.datalib.request_bean.builder.InitiateVerificationBeanBuilder;
import com.kaurihealth.datalib.request_bean.builder.RequestResetUserPasswordBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IChangePasswordService;
import com.kaurihealth.datalib.service.IRegisterService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.resetpassword_v.ResetPasswordActivity;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.EdtUtil;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.Broadcast.BroadcastFactory;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.TimeCount;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 找回密码
 * 修订日期:
 */
public class FindpasswordActivity extends CommonActivity {
    @Bind(R.id.edt_phone_number)
    EditText edtPhonenumber;
    @Bind(R.id.tv_errorphone)
    TextView tvErrorphone;
    @Bind(R.id.edt_verification_code)
    EditText edtVerificationCode;
    @Bind(R.id.tv_get_verificationcode)
    TextView tvGetVerificationcode;
    @Bind(R.id.tv_errorversioncode)
    TextView tvErrorversioncode;
    private TimeCount timeCount;
    private String versionCode;
    private String phoneNumber;
    private BroadcastReceiver broadcastReceiver;

    private IRegisterService registerService;
    private IChangePasswordService changePasswordService;

    private final int resetPass = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = BroadcastFactory.getSmsIntentFileter();
        broadcastReceiver = BroadcastFactory.getSmsReceiver(edtVerificationCode);
        this.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        String phonenumber = getBundle().getString("phonenumber");
        if (phonenumber != null) {
            edtPhonenumber.setText(phonenumber);
        }
        TextView[] textViews = {tvErrorphone, tvErrorversioncode};
        EdtUtil.monitorEdittext(edtPhonenumber, textViews);
        EdtUtil.monitorEdittext(edtVerificationCode, textViews);
        ServiceFactory serviceFactory = new ServiceFactory(Url.prefix,getApplicationContext());
        registerService = serviceFactory.getRegisterService();
        changePasswordService = serviceFactory.getChangePasswordService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unregisterReceiver(broadcastReceiver);
    }


    /**
     * 获取验证码
     */
    @OnClick(R.id.tv_get_verificationcode)
    public void getVerificationcode() {
        String phoneNumber = edtPhonenumber.getText().toString().trim();
        boolean state = true;
        if (!showVerfy(phoneNumber.matches("\\d{11,}"), tvErrorphone, "手机号码有误")) {
            state = false;
        }
        if (state) {
            getVersionCode(phoneNumber);
            startCountDown();
        }
    }


    //监测电话号码
    private boolean checkPhone() {
        phoneNumber = edtPhonenumber.getText().toString().trim();
        return showVerfy(phoneNumber.length() >= 11, tvErrorphone, "手机长度有误") && showVerfy(phoneNumber.matches("\\d+"), tvErrorphone, "手机号码格式有误");
    }

    //监测验证码
    private boolean checkVersionCode() {
        versionCode = edtVerificationCode.getText().toString().trim();
        return showVerfy(!TextUtils.isEmpty(versionCode), tvErrorversioncode, "手机验证码不能为空");
    }



    /**
     * 提交
     */
    @OnClick(R.id.tv_submit)
    public void submit() {
        if (checkPhone() && checkVersionCode()) {
            RequestResetPasswordDisplayDto newRequestResetUserPasswordBean = new RequestResetUserPasswordBuilder().Build(phoneNumber, versionCode);
            Call<ResponseDisplayBean> call = changePasswordService.RequestResetUserPassword_out(newRequestResetUserPasswordBean);
            call.enqueue(new Callback<ResponseDisplayBean>() {
                @Override
                public void onResponse(Call<ResponseDisplayBean> call, retrofit2.Response<ResponseDisplayBean> response) {
                    if (response.isSuccessful()) {
                        Bundle bundle = new Bundle();
                        bundle.putString("hashCode", response.body().getMessage());
                        bundle.putString("phoneNumber", edtPhonenumber.getText().toString().trim());
                        skipToForResult(ResetPasswordActivity.class, bundle, resetPass);
                    } else {
                        showToast("发送请求失败");
                    }
                }

                @Override  //反正网络框架无响应
                public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                    showToast(LoadingStatu.NetError.value);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case resetPass:
                if (resultCode == RESULT_OK) {
                    setResult(RESULT_OK, data);
                    finishCur();
                }
                break;
        }
    }

    //网络请求短信获取验证码
    private void getVersionCode(String phoneNumber) {
        InitiateVerificationBean initiateVerificationBean = new InitiateVerificationBeanBuilder().BuildResetPasswordInitiateVerification(phoneNumber);
        Call<InitiateVerificationResponse> call = registerService.InitiateVerification_call(initiateVerificationBean);
        call.enqueue(new Callback<InitiateVerificationResponse>() {
            @Override
            public void onResponse(Call<InitiateVerificationResponse> call, retrofit2.Response<InitiateVerificationResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccessful) {
                        showToast("发送验证码成功");
                    } else {
                        showToast(response.body().message);
                    }
                } else {
                    showToast("发送验证码失败");
                }
            }

            @Override
            public void onFailure(Call<InitiateVerificationResponse> call, Throwable t) {
                showToast(LoadingStatu.NetError.value);
            }
        });
    }

    /**
     * @param result
     * @param tvShow
     * @param errorMessage 错误的时候的提示内容
     */
    private boolean showVerfy(boolean result, TextView tvShow, String errorMessage) {
        if (result) {
            tvShow.setVisibility(View.GONE);
        } else {
            tvShow.setVisibility(View.VISIBLE);
            tvShow.setText(errorMessage);
        }
        return result;
    }

    //获取验证码进行倒计
    private void startCountDown() {
        if (timeCount == null) {
            timeCount = new TimeCount(60000, 1000, tvGetVerificationcode, "%d秒后重新获取", "获取验证码");
        }
        timeCount.start();
    }
}
