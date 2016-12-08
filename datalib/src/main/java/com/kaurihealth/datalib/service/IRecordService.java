package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 介绍：临床诊疗记录
 */
public interface IRecordService {
    @POST("api/Record/UpdatePatientRecord")
    Call<PatientRecordDisplayBean> UpdatePatientRecord(@Body PatientRecordDisplayBean bean);

    /**
     * 更新患者临床诊疗记录 医生和患者的token都可以
     */
    @POST("api/Record/UpdatePatientRecord")
    Observable<PatientRecordDisplayBean> updatePatientRecord(@Body PatientRecordDisplayBean bean);

    @POST("api/Record/AddNewPatientRecord")
    Call<PatientRecordDisplayBean> AddNewPatientRecord(@Body NewPatientRecordDisplayBean bean);

    /**
     * 插入新的临床诊疗记录 医生和患者的token都可以
     */
    @POST("api/Record/AddNewPatientRecord")
    Observable<PatientRecordDisplayBean> addNewPatientRecord(@Body NewPatientRecordDisplayBean bean);
}
