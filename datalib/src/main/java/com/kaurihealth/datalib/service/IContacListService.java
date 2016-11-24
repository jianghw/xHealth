package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Nick on 21/04/2016.
 * <p/>
 * Describe:
 */
public interface IContacListService {

    @GET("api/ContactList/LoadContactListByDoctorId")
    Observable<List<ContactUserDisplayBean>> loadContactListByDoctorId();

    /**
     * 根据主ID集合查询相关信息
     *
     * @return
     */
    @POST("api/ContactList/LoadContactListByKauriHealthIds")
    Observable<List<ContactUserDisplayBean>> loadContactListByKauriHealthIds(@Body List<String> id);
}
