package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.NewDoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public interface IDoctorRelationshipService {
    @GET("api/DoctorRelationship/LoadDoctorRelationships")
    Call<List<DoctorRelationshipBean>> LoadDoctorRelationships();

    //开启新的医医关系
    @POST("api/DoctorRelationship/RequestNewDoctorRelationship")
    Call<ResponseDisplayBean> RequestNewDoctorRelationship(@Body NewDoctorRelationshipBean bean);
}
