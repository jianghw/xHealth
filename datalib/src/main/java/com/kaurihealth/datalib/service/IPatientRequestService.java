package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.PatientRequestDecisionBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Nick on 22/04/2016.
 */
public interface IPatientRequestService {

    //通过医生查询患者的请求
    @GET("api/PatientRequest/LoadPatientRequestsByDoctor")
    Call<List<PatientRequestDisplayBean>> LoadPatientRequestsByDoctor();

    //接受患者的请求
    @POST("api/PatientRequest/AcceptPatientRequest")
    Call<ResponseDisplayBean> AcceptPatientRequest(@Body PatientRequestDecisionBean patientRequestDecisionBean);

    //拒绝患者的请求
    @POST("api/PatientRequest/RejectPatientRequest")
    Call<ResponseDisplayBean> RejectPatientRequest(@Body PatientRequestDecisionBean patientRequestDecisionBean);

}
