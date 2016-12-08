package com.kaurihealth.datalib.service;



import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 张磊 on 2016/6/7.
 * 介绍：
 */
public interface ILabTestService {
    //更新实验室检查
    @POST("api/LabTest/UpdateLabTest")
    Call<PatientRecordDisplayBean> UpdateLabTest(@Body PatientRecordDisplayBean bean);

    @POST("api/LabTest/UpdateLabTest")
    Observable<PatientRecordDisplayBean> UpdateLabTest_new(@Body PatientRecordDisplayBean bean);

    //插入实验室检查
    @POST("api/LabTest/InsertLabTest")
    Call<PatientRecordDisplayBean> InsertLabTest(@Body NewLabTestPatientRecordDisplayBean bean);

    @POST("api/LabTest/InsertLabTest")
    Observable<PatientRecordDisplayBean> InsertLabTest_new(@Body NewLabTestPatientRecordDisplayBean bean);
}
