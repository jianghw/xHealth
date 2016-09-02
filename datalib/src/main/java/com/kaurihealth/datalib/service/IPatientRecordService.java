package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 张磊 on 2016/6/7.
 * 介绍：通过患者ID查询患者的医疗记录
 */
public interface IPatientRecordService {
    @GET("api/PatientRecord/LoadAllPatientGenericRecordsBypatientId")
    Call<List<PatientRecordDisplayBean>> LoadAllPatientGenericRecordsBypatientId(@Query("patientId") int patientId);
}
