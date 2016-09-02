package com.kaurihealth.datalib.service;




import com.kaurihealth.datalib.response_bean.PatientDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by Nick on 21/04/2016.
 */
public interface IPatientService {
    @GET("api/Patient/LoadPatientDetail")
    Call<PatientDisplayBean> LoadPatientDetail();

    @GET("api/Patient/SearchPatientByUserName")
    Call<List<PatientDisplayBean>> SearchPatientByUserName_out(@Query("userName") String userName);

    @GET("api/Patient/SearchPatientByUserName")
    Observable<List<PatientDisplayBean>> SearchPatientByUserName(@Query("userName") String userName);
}
