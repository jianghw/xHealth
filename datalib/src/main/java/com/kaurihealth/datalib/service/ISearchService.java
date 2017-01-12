package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Nick on 17/05/2016.
 */
public interface ISearchService {
    @POST("api/Search")
    Call<SearchResultBean> KeywordSearch_out(@Body InitialiseSearchRequestBean initialiseSearchRequestBean);

    @POST("api/Search")
    Observable<SearchResultBean> KeywordSearch(@Body InitialiseSearchRequestBean initialiseSearchRequestBean);

    @POST("api/Search/PatientSearch")
    Observable<List<MainPagePatientDisplayBean>> PatientSearch(@Body InitialiseSearchRequestBean initialiseSearchRequestBean);
}
