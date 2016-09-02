package com.kaurihealth.datalib.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by miping on 2016/7/16.
 */
public interface IHospitalsService {

    @GET("api/Hospital/LoadAllHospitals")
    Call<List<String>> LoadAllHospitals();
}
