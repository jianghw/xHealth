package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 张磊 on 2016/6/7.
 * 介绍：
 */
public interface ISupplementaryTestService {
    //更新辅助检查
    @POST("api/SupplementaryTest/UpdateSupplementaryTest")
    Call<PatientRecordDisplayBean> UpdateSupplementaryTest(@Body PatientRecordDisplayBean bean);

    @POST("api/SupplementaryTest/UpdateSupplementaryTest")
    Observable<PatientRecordDisplayBean> UpdateSupplementaryTest_new(@Body PatientRecordDisplayBean bean);
    //插入辅助检查
    @POST("api/SupplementaryTest/InsertSupplementaryTest")
    Call<PatientRecordDisplayBean> InsertSupplementaryTest(@Body NewSupplementaryTestPatientRecordDisplayBean bean);

    @POST("api/SupplementaryTest/InsertSupplementaryTest")
    Observable<PatientRecordDisplayBean> InsertSupplementaryTest_new(@Body NewSupplementaryTestPatientRecordDisplayBean bean);
}
