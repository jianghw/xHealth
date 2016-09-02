package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Nick on 24/04/2016.
 */
public interface IDepartmentService {
    @GET("api/Department/LoadAllDepartment")
    Call<List<DepartmentDisplayBean>> LoadAllDepartment();
}
