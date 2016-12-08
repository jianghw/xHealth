package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.request_bean.bean.NewFamilyMemberBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Garnet_Wu on 2016/11/11.
 */
public interface IFamilyGroupService {
    //医生为添加家庭成员   必须为医生token
    @POST("api/FamilyGroup/AddFamilyMemberByDoctor")
    Observable<ResponseDisplayBean> addFamilyMemberByDoctor(@Body NewFamilyMemberBean newFamilyMemberBean);


    //查询所有的家庭成员
    @GET("api/FamilyGroup/LoadAllFamilyMembers")
    Observable<List<FamilyMemberBean>> loadAllFamilyMembers(@Query("patientId") int patientId);
}
