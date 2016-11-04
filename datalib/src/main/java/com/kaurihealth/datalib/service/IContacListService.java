package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Nick on 21/04/2016.
 */
public interface IContacListService {

    @GET("api/ContactList/LoadContactListByDoctorId")
    Observable<List<ContactUserDisplayBean>> loadContactListByDoctorId();
}
