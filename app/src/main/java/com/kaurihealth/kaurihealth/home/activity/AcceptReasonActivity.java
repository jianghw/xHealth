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
 * 描述：
 * 修订日期:
 */

/**
 * 接受请求
 */
public class AcceptReasonActivity extends CommonActivity implements Validator.ValidationListener {


    @NotEmpty(message = "请对患者说些什么吧~")
    @Bind(R.id.edt_name)
    EditText edt_name;
    private Validator validator;

    private IPatientRequestService patientRequestService;
    private PatientRequestDecisionBean requestDecisionBean;

    private int patientRequestId;

    public static final int AcceptReason = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_reason);
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
        //注册表单校验器
        validator = new Validator(this);
        validator.setValidationListener(this);
        patientRequestService = new ServiceFactory(Url.prefix,getApplicationContext()).getPatientRequestService();
    }

    /**
     * 完成
     */
    @OnClick(R.id.tv_operate)
    public void complete() {
        validator.validate();

    }


    //表单校验通过
    @Override
    public void onValidationSucceeded() {
        final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
        requestDecisionBean = new PatientRequestDecisionBeanBuilder().Build(patientRequestId, edt_name.getText().toString().trim());
        Call<ResponseDisplayBean> responseDisplayBeanCall = patientRequestService.AcceptPatientRequest(requestDecisionBean);
        responseDisplayBeanCall.enqueue(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                if (response.isSuccessful()) {
                    setResult(RESULT_OK);
                    loadingUtil.dismiss("患者预约接受成功！", new LoadingUtil.Success() {
                        @Override
                        public void dismiss() {
                            finishCur();
                        }
                    });

                } else {
                    loadingUtil.dismiss("留言填写失败，错误码为："+response.code());
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
