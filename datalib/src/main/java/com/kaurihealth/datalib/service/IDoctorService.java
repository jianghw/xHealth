package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.datalib.request_bean.bean.MobileUpdateDoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Nick on 24/04/2016.
 */
public interface IDoctorService {
    @GET("api/Doctor/LoadDoctorDetail_out")
    Call<DoctorDisplayBean> LoadDoctorDetail_out();

    @GET("api/Doctor/LoadDoctorDetail")
    Observable<DoctorDisplayBean> LoadDoctorDetail();

    @POST("api/Doctor/UpdateDoctor")
    Observable<DoctorDisplayBean> UpdateDoctor(@Body DoctorDisplayBean doctorDisplayBean);


    @POST("api/Doctor/UpdateDoctorUserInformation")
    Observable<ResponseDisplayBean> UpdateDoctorUserInformation(@Body DoctorUserBean doctorUserBean);

    @POST("api/Doctor/MobileUpdateDoctor")
    Call<ResponseDisplayBean> MobileUpdateDoctor(@Body MobileUpdateDoctorDisplayBean bean);
}
