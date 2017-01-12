package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordCountDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 张磊 on 2016/6/7.
 * 患者的病例  PatientRecord
 */
public interface IPatientRecordService {
    @GET("api/PatientRecord/LoadAllPatientGenericRecordsBypatientId")
    Call<List<PatientRecordDisplayBean>> LoadAllPatientGenericRecordsBypatientId(@Query("patientId") int patientId);

    /**
     * 介绍：通过患者ID查询患者的医疗记录
     *
     * @param patientId
     * @return
     */
    @GET("api/PatientRecord/LoadAllPatientGenericRecordsBypatientId")
    Observable<List<PatientRecordDisplayBean>> loadAllPatientGenericRecordsBypatientId(@Query("patientId") int patientId);

    /**
     * 通过医生查询转诊状态为等待的请求
     *
     * @return
     */
    @GET("api/PatientRequest/LoadReferralsPatientRequestByDoctorId")
    Observable<List<PatientRequestDisplayBean>> LoadReferralsPatientRequestByDoctorId();

    /**
     * 病理详情api
     */
    @GET("api/PatientRecord/LoadNewPatientRecordCountsByPatientId")
    Observable<PatientRecordCountDisplayBean> loadNewPatientRecordCountsByPatientId(@Query("patientId") int patientId);

    /**
     * 通过患者Id和病例类型查询该患者对应的的所有病例
     */
    @GET("api/PatientRecord/LoadAllPatientGenericRecordsBypatientIdAndCategory")
    Observable<List<PatientRecordDisplayDto>> loadAllPatientGenericRecordsBypatientIdAndCategory(@Query("patientId") int patientId, @Query("category") String category);
}
