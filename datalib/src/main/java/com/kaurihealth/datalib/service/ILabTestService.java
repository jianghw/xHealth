package com.kaurihealth.datalib.service;



import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 张磊 on 2016/6/7.
 * 介绍：
 */
public interface ILabTestService {
    //更新实验室检查
    @POST("api/LabTest/UpdateLabTest")
    Call<PatientRecordDisplayBean> UpdateLabTest(@Body PatientRecordDisplayBean bean);
    //插入实验室检查
    @POST("api/LabTest/InsertLabTest")
    Call<PatientRecordDisplayBean> InsertLabTest(@Body NewLabTestPatientRecordDisplayBean bean);
}
