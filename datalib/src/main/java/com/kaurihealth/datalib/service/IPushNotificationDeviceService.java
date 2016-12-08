package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2016/11/11.
 * <p/>
 * Describe:
 */
public interface IPushNotificationDeviceService {

    //插入推送设备 医生使用的token
    @FormUrlEncoded
    @POST("api/PushNotificationDevice/InsertNewPushNotificationDevice")
    Observable<ResponseDisplayBean> insertNewPushNotificationDevice(@Field("IdentityToken") String identityToken, @Field("DeviceType") String deviceType);
}
