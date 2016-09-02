package com.kaurihealth.datalib.service;



import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.request_bean.bean.NewRegisterBean;
import com.kaurihealth.datalib.request_bean.bean.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Nick on 23/04/2016.
 */
public interface IRegisterService {
    //过时:医生帮患者快速注册
    @POST("api/Register/RegistByDoctor")
    Call<RegisterResponse> RegistByDoctor_out(@Body NewRegistByDoctorBean newRegistByDoctorBean);

    @POST("api/Register/InitiateVerification")
    Call<InitiateVerificationResponse> InitiateVerification_call(@Body InitiateVerificationBean initiateVerificationBean);

    @POST("api/Register/InitiateVerification") //发送验证码： regist或者resetpassword
   Observable<InitiateVerificationResponse> InitiateVerification(@Body InitiateVerificationBean initiateVerificationBean);

    @POST("api/Regist")
    Observable<RegisterResponse> Regist(@Body NewRegisterBean newRegisterBean);

    //医生帮患者快速注册
    @POST("api/Register/RegistByDoctor")
    Observable<RegisterResponse> RegistByDoctor(@Body NewRegistByDoctorBean newRegistByDoctorBean);

}
