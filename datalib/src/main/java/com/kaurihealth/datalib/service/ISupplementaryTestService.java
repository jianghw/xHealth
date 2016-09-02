package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 张磊 on 2016/6/7.
 * 介绍：
 */
public interface ISupplementaryTestService {
    //更新辅助检查
    @POST("api/SupplementaryTest/UpdateSupplementaryTest")
    Call<PatientRecordDisplayBean> UpdateSupplementaryTest(@Body PatientRecordDisplayBean bean);
    //插入辅助检查
    @POST("api/SupplementaryTest/InsertSupplementaryTest")
    Call<PatientRecordDisplayBean> InsertSupplementaryTest(@Body NewSupplementaryTestPatientRecordDisplayBean bean);
}
