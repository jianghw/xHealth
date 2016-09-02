package com.kaurihealth.kaurihealth.services;

import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.kaurihealth.util.Url;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Nick on 21/04/2016.
 */
public class AuthenticationService implements IAuthenticationService {
    private Retrofit retrofit;
    public AuthenticationService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.prefix)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }


    @Override
    public ILoginService getLoginService() {
        return retrofit.create(ILoginService.class);
    }

    @Override
    public Retrofit getRetrofit() {
        return retrofit;
    }
}
