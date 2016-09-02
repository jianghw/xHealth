package com.kaurihealth.datalib.service;



import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by 张磊 on 2016/6/7.
 * 介绍：
 */
public interface IPathologyRecordService {
    //更新病理检查
    @POST("api/PathologyRecord/UpdatePathologyRecord")
    Call<PatientRecordDisplayBean> UpdatePathologyRecord(@Body PatientRecordDisplayBean bean);
    //插入病理检查
    @POST("api/PathologyRecord/InsertPathologyRecord")
    Call<PatientRecordDisplayBean> InsertPathologyRecord(@Body NewPathologyPatientRecordDisplayBean bean);
}
