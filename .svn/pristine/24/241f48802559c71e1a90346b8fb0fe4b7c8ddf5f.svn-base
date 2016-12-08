package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.datalib.request_bean.bean.MobileUpdateDoctorBean;
import com.kaurihealth.datalib.request_bean.bean.MobileUpdateDoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Nick on 24/04/2016.
 * <p/>
 * 描述：
 */
public interface IDoctorService {
    @GET("api/Doctor/LoadDoctorDetail_out")
    Call<DoctorDisplayBean> LoadDoctorDetail_out();

    /**
     * 查询医生信息
     */
    @GET("api/Doctor/LoadDoctorDetail")
    Observable<DoctorDisplayBean> loadDoctorDetail();

    /**
     * 更新医生的信息
     */
    @POST("api/Doctor/UpdateDoctor")
    Observable<DoctorDisplayBean> UpdateDoctor(@Body DoctorDisplayBean doctorDisplayBean);

    /**
     * 更新医生用户的信息
     */
    @POST("api/Doctor/UpdateDoctorUserInformation")
    Observable<ResponseDisplayBean> UpdateDoctorUserInformation(@Body DoctorUserBean doctorUserBean);

    @POST("api/Doctor/MobileUpdateDoctor")
    Call<ResponseDisplayBean> MobileUpdateDoctor(@Body MobileUpdateDoctorDisplayBean bean);

    //移动端更新医生信息
    @POST("api/Doctor/MobileUpdateDoctor")
    Observable<ResponseDisplayBean> MobileUpdateDoctor(@Body MobileUpdateDoctorBean bean);

    /**
     * 通过医生ID查询医生的信息
     */
    @GET("api/Doctor/LoadDoctorDetailByDoctorId")
    Observable<DoctorDisplayBean> LoadDoctorDetailByDoctorId(@Query("doctorId") int doctorId);
}
