package com.kaurihealth.datalib.service;


import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.request_bean.bean.RefreshBean;
import com.kaurihealth.datalib.response_bean.TokenBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;


/**
 * Created by Nick on 21/04/2016.
 */
public interface ILoginService {
    @POST("api/Login")
    Observable<TokenBean> Login(@Body LoginBean loginBean);

    @POST("api/Login/Refresh")
    Call<TokenBean> Refresh(@Body RefreshBean refreshBean);
}
