package com.kaurihealth.datalib.service;



import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 张磊 on 2016/6/7.
 * 介绍：
 */
public interface IRecordService {
    @POST("api/Record/UpdatePatientRecord")
    Call<PatientRecordDisplayBean> UpdatePatientRecord(@Body PatientRecordDisplayBean bean);

    @POST("api/Record/AddNewPatientRecord")
    Call<PatientRecordDisplayBean> AddNewPatientRecord(@Body NewPatientRecordDisplayBean bean);
}
