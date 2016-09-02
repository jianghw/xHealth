package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewHealthConditionBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Nick on 21/04/2016.
 */
public interface IHealthConditionService {

    @GET("api/HealthCondition/LoadHealthConditionByPatientId")
    Call<List<HealthConditionDisplayBean>> LoadHealthConditionByPatientId(@QueryMap Map<String, String> option);

    @GET("api/HealthCondition/LoadHealthCondition")
    Call<List<HealthConditionDisplayBean>> LoadHealthCondition();

    @POST("api/HealthCondition/InsertHealthCondition")
    Call<HealthConditionDisplayBean> InsertHealthCondition(@Body NewHealthConditionBean newHealthConditionBean);

    @POST("api/HealthCondition/UpdateHealthConditions")
    Call<HealthConditionDisplayBean> UpdateHealthConditions(@Body HealthConditionDisplayBean[] healthConditionDisplayBeans);

    @POST("api/HealthCondition/DeleteHealthConditions")
    Call<ResponseDisplayBean> DeleteHealthConditions(@Body Integer[] ids);
}
