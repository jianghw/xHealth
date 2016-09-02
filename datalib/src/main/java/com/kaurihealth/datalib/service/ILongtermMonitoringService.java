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

/**
 * Created by Nick on 28/04/2016.
 */
public interface ILongtermMonitoringService {

    /**
     * 通过患者ID查询长期监测 医生和患者的token都可以用
     *
     * @param patientId
     * @return
     */
    @GET("api/LongtermMonitoring/LoadLongtermMonitoringByPatientId")
    Observable<List<LongTermMonitoringDisplayBean>> longTermMonitoringDisplay(@Query("patientId") int patientId);

    /**
     * 插入长期监测集合
     *
     * @param bean
     * @return
     */
    @POST("api/LongtermMonitoring/InsertLongtermMonitorings")
    Observable<ResponseDisplayBean> insertLongtermMonitorings(@Body List<NewLongtermMonitoringBean> bean);

    /**
     * 更新多个长期监测 医生和患者的token都可以用
     *
     * @param bean
     * @return
     */
    @POST("api/LongtermMonitoring/UpdateLongtermMonitorings")
    Observable<List<LongTermMonitoringDisplayBean>> updateLongtermMonitorings(@Body List<LongTermMonitoringDisplayBean> bean);
}
