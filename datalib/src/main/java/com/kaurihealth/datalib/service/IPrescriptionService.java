package com.kaurihealth.datalib.service;



import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Nick on 22/04/2016.
 */
public interface IPrescriptionService {
    @GET("api/Prescription/LoadPrescriptionsByPatientId")
    Call<List<PrescriptionBean>> LoadPrescriptionsByPatientId(@Query("patientId") int patientId);

    @POST("api/Prescription/InsertPrescription")
    Call<PrescriptionBean> InsertPrescription(@Body NewPrescriptionBean newPrescriptionBean);
}
