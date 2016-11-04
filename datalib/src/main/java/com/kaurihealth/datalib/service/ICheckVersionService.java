package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.response_bean.SoftwareInfo;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by miping on 2016/7/6.
 */
public interface ICheckVersionService {
    @GET("AndroidVersion/GetLatestDoctorAppVersion")
    Observable<SoftwareInfo> CheckVersion(@QueryMap Map<String,String> options);
}
