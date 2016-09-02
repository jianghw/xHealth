package com.kaurihealth.datalib.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianghw on 2016/8/18.
 * <p/>
 * 描述：不用传token 还 同步请求
 */
public class NoTokenExecuteRetrofit implements IRetrofit {
    @Override
    public Retrofit createRetrofit(String url) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
