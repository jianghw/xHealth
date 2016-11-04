package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Nick on 24/04/2016.
 */
public interface IDepartmentService {
    @GET("api/Department/LoadAllDepartment")
    Call<List<DepartmentDisplayBean>> LoadAllDepartment_out();

    @GET("api/Department/LoadAllDepartment")
    Observable<List<DepartmentDisplayBean>> LoadAllDepartment();

    //加载一级科室
    @GET("api/Department/LoadDepartment?level=1")
    Observable<List<DepartmentDisplayBean>> LoadDepartment();

    //加载二级子类科室
    /*@GET("api/Department/LoadDepartmentName?level=2&{parent}")
    Observable<List<DepartmentDisplayBean>> LoadDepartmentName(@Path("parent") int parentDepartmentId );*/

   /* @GET("api/Department/LoadDepartmentName?level=2&")
        Observable<List<DepartmentDisplayBean>> LoadDepartmentName(@Query("parent") int parentDepartmentId );*/

    //加载二级子类科室
    @GET("api/Department/LoadDepartmentName")
        Observable<List<DepartmentDisplayBean>> LoadDepartmentName(@Query("level") int level , @Query("parent") int parentDepartmentId );
}
