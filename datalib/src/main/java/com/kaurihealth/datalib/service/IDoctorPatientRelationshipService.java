package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Nick on 21/04/2016.
 * 为现医生查询所有医患关系
 */
public interface IDoctorPatientRelationshipService {

    @GET("api/DoctorPatientRelationship/LoadDoctorPatientRelationshipForDoctor")
    Call<List<com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean>> LoadDoctorPatientRelationshipForDoctor();

    /**
     * 为现医生查询所有医患关系
     *
     * @return
     */
    @GET("api/DoctorPatientRelationship/LoadDoctorPatientRelationshipForDoctor")
    Observable<List<DoctorPatientRelationshipBean>> loadDoctorPatientRelationshipForDoctor();

    @POST("api/DoctorPatientRelationship/InsertNewRelationshipByDoctor")
    Call<com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean> InsertNewRelationshipByDoctor(@Body int patientId);

    /**
     * 医生创建普通病人关系(复诊)
     *
     * @param patientId
     * @return
     */
    @POST("api/DoctorPatientRelationship/InsertNewRelationshipByDoctor")
    Observable<DoctorPatientRelationshipBean> insertNewRelationshipByDoctor(@Body int patientId);

    /**
     * 申请结束医患关系
     *
     * @param doctorPatientReplationshipId
     * @return
     */
    @POST("api/DoctorPatientRelationship/RequestEndDoctorPatientRelationship")
    Call<ResponseDisplayBean> RequestEndDoctorPatientRelationship(@Body int doctorPatientReplationshipId);

    /**
     * 使用患者id查询医患关系
     *
     * @param patientId
     * @return
     */
    @GET("api/DoctorPatientRelationship/LoadDoctorPatientRelationshipForPatientId")
    Call<List<com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean>> LoadDoctorPatientRelationshipForPatientId(@Query("patientId") int patientId);

}
