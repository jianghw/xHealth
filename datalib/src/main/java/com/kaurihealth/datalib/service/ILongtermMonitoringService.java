package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.NewLongtermMonitoringBean;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ILongtermMonitoringService {

    /**
     * 通过患者ID查询长期监测 医生和患者的token都可以用
     */
    @GET("api/LongtermMonitoring/LoadLongtermMonitoringByPatientId")
    Observable<List<LongTermMonitoringDisplayBean>> loadLongtermMonitoringByPatientId(@Query("patientId") int patientId);

    /**
     * 插入长期监测集合
     */
    @POST("api/LongtermMonitoring/InsertLongtermMonitorings")
    Observable<ResponseDisplayBean> insertLongtermMonitorings(@Body List<NewLongtermMonitoringBean> bean);

    /**
     * 更新多个长期监测 医生和患者的token都可以用
     */
    @POST("api/LongtermMonitoring/UpdateLongtermMonitorings")
    Observable<List<LongTermMonitoringDisplayBean>> updateLongtermMonitorings(@Body List<LongTermMonitoringDisplayBean> bean);

    /**
     * 删除多个长期监测
     */
    @POST("api/LongtermMonitoring/DeleteLongtermMonitorings")
    Observable<ResponseDisplayBean> deleteLongtermMonitorings(@Body List<Integer> bean);
}
