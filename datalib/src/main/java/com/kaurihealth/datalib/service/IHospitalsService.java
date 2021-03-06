package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.request_bean.bean.NewHospitalizationPatientRecordDisplayDto;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by miping on 2016/7/16.
 */
public interface IHospitalsService {

    @GET("api/Hospital/LoadAllHospitals")
    Call<List<String>> LoadAllHospitals_out();

    @GET("api/Hospital/LoadAllHospitals")
    Observable<List<String>> loadAllHospitals();

    /**
     * 更新
     */
    @POST("api/Hospitalization/UpdateHospitalization")
    Observable<PatientRecordDisplayDto> UpdateHospitalization(@Body PatientRecordDisplayDto bean);

    /**
     * 插入
     */
    @POST("api/Hospitalization/InsertHospitalization")
    Observable<PatientRecordDisplayDto> InsertHospitalization(@Body NewHospitalizationPatientRecordDisplayDto bean);

}
