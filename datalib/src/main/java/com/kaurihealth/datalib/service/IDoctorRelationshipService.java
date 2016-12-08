package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.NewDoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public interface IDoctorRelationshipService {
    @GET("api/DoctorRelationship/LoadDoctorRelationships")
    Call<List<DoctorRelationshipBean>> LoadDoctorRelationships();

    /**
     * 查询用户医生关系的病例（不包含拒绝的） 医生使用的token
     *
     * @return
     */
    @GET("api/DoctorRelationship/LoadDoctorRelationships")
    Observable<List<DoctorRelationshipBean>> loadDoctorRelationships();

    //开启新的医医关系
    @POST("api/DoctorRelationship/RequestNewDoctorRelationship")
    Call<ResponseDisplayBean> RequestNewDoctorRelationship(@Body NewDoctorRelationshipBean bean);

    @POST("api/DoctorRelationship/RequestNewDoctorRelationship")
    Observable<ResponseDisplayBean> requestNewDoctorRelationship(@Body NewDoctorRelationshipBean bean);

    //加载所有挂起的医生关系
    @GET("api/DoctorRelationship/LoadPendingDoctorRelationships")
    Observable<List<DoctorRelationshipBean>> LoadPendingDoctorRelationships();

    //通过医生关系id接受医生关系
    @POST("api/DoctorRelationship/AcceptDoctorRelationship")
    Observable<ResponseDisplayBean> AcceptDoctorRelationship(@Body String DoctorRelationshipId);

    //通过医生id拒绝医生关系
    @POST("api/DoctorRelationship/RejectDoctorRelationship")
    Observable<ResponseDisplayBean> RejectDoctorRelationship(@Body String DoctorRelationshipId);

    /**
     * 查询所有用户医生关系的病例（包含拒绝的） 医生使用的token
     *
     * @return
     */
    @GET("api/DoctorRelationship/LoadAllDoctorRelationships")
    Observable<List<DoctorRelationshipBean>> LoadAllDoctorRelationships();

    //结束医生关联 医生使用的token
    @DELETE("api/DoctorRelationship/RemoveDoctorRelationship")
    Observable<ResponseDisplayBean> loadContactListByDoctorId(@Query("doctorRelationshipId") int id, @Query("comment") String comment);
}
