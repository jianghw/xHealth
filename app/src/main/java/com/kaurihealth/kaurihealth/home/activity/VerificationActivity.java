package com.kaurihealth.kaurihealth.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.NewDoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IDoctorRelationshipService;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.util.CommonActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerificationActivity extends CommonActivity {

    @Bind(R.id.edtVerification)
    EditText edtVerification;
    private Bundle bundle;
    private int relatedDoctorId;
    private IDoctorRelationshipService iDoctorRelationshipService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verification_activity);
        setBack(R.id.iv_back);
        ButterKnife.bind(this);
        initService();
        initData();
    }

    private void initData() {
        bundle = getBundle();
        relatedDoctorId = bundle.getInt("relatedDoctorId", -1);
    }

    private void initService() {
        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix, this);
        iDoctorRelationshipService = serviceFactory.getIDoctorRelationshipService();
    }

    @OnClick({R.id.tv_operate})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_operate:
                addNewFriend();
                break;
        }
    }

    private void addNewFriend() {
        if (relatedDoctorId != -1) {
            NewDoctorRelationshipBean requestBean = new NewDoctorRelationshipBean();
            requestBean.relatedDoctorId = relatedDoctorId;
            requestBean.description = edtVerification.getText().toString();
            final Bundle data = bundle;
            Call<ResponseDisplayBean> responseDisplayBeanCall = iDoctorRelationshipService.RequestNewDoctorRelationship(requestBean);
            final LoadingUtil loadingUtil = LoadingUtil.getInstance(this);
            loadingUtil.show();
            responseDisplayBeanCall.enqueue(new Callback<ResponseDisplayBean>() {
                @Override
                public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                    if (response.isSuccessful()) {
                        loadingUtil.dismiss("发送成功", new LoadingUtil.Success() {
                            @Override
                            public void dismiss() {
                                Intent intent = new Intent();
                                intent.putExtras(data);
                                setResult(RESULT_OK, intent);
                                finishCur();
                            }
                        });
                    } else {

                        loadingUtil.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                    loadingUtil.dismiss();
                }
            });
        }
    }
}
