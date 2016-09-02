package com.kaurihealth.kaurihealth.services;

import com.kaurihealth.datalib.service.ILoginService;

import retrofit2.Retrofit;

/**
 * Created by Nick on 21/04/2016.
 */
public interface IAuthenticationService {
    ILoginService getLoginService();

    Retrofit getRetrofit();
}
