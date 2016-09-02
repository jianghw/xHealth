package com.kaurihealth.kaurihealth.home.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDecisionBean;
import com.kaurihealth.datalib.request_bean.builder.PatientRequestDecisionBeanBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IPatientRequestService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Url;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：  拒绝患者请求
 * 修订日期:
 */
public class RejectReasonActivity extends CommonActivity implements Validator.ValidationListener {

    private int patientRequestId;
    @NotEmpty(message = "请输入您的理由")
    @Bind(R.id.edt_name)
    EditText edt_name;

    private Validator validator;
    private PatientRequestDecisionBean patientRequestDecisionBean;
    private IPatientRequestService patientRequestService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reject_reason);
        ButterKnife.bind(this);
        init();
        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void init() {
        super.init();
        setBack(R.id.iv_back);
        Bundle bundle = getBundle();
        patientRequestId = bundle.getInt("PatientRequestId");
        //表单校验器初始化
        validator = new Validator(this);
        validator.setValidationListener(this);
        //获取token
        patientRequestService = new ServiceFactory(Url.prefix,getApplicationContext()).getPatientRequestService();

    }

    /**
     * 完成
     */
    @OnClick(R.id.tv_operate)
    public void complete() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
        patientRequestDecisionBean = new PatientRequestDecisionBeanBuilder().Build(patientRequestId, edt_name.getText().toString().trim());
        Call<ResponseDisplayBean> call = patientRequestService.RejectPatientRequest(patientRequestDecisionBean);
        call.enqueue(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                if (response.isSuccessful()) {
                    loadingUtil.dismiss("患者预约已拒绝！", new LoadingUtil.Success() {
                        @Override
                        public void dismiss() {
                            setResult(RESULT_OK);
                            finishCur();
                        }
                    });

                } else {
                    loadingUtil.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                loadingUtil.dismiss(LoadingStatu.NetError.value);
            }
        });
        loadingUtil.show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        showValidationMessage(errors);
    }
}
