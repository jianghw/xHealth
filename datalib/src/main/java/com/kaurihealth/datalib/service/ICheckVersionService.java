package com.kaurihealth.datalib.service;


import com.example.commonlibrary.widget.bean.SoftwareInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by miping on 2016/7/6.
 */
public interface ICheckVersionService {
    @GET("AndroidVersion/GetLatestDoctorAppVersion")
    Call<SoftwareInfo> CheckVersion(@QueryMap Map<String,String> options);
}
