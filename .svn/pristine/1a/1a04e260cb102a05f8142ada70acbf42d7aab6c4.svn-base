package com.kaurihealth.datalib.remote;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.utilslib.constant.Global;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianghw on 2016/8/18.
 * <p/>
 * 描述：不用传token 还 同步请求
 */
class NoTokenExecuteRetrofit implements IRetrofit {
    private final OkHttpClient client;

    private static class SingletonHolder {
        private static final NoTokenExecuteRetrofit INSTANCE = new NoTokenExecuteRetrofit();
    }

    public static NoTokenExecuteRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    NoTokenExecuteRetrofit() {
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(20, TimeUnit.SECONDS)
//                .sslSocketFactory(RetrofitFactory.getInstance().getSslSocketFactory())
                .build();
    }

    @Override
    public Retrofit createRetrofit(String url) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    //OkHttpClient并设置超时时间
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("OKHttp", message);
                }
            })
            .setLevel(!LocalData.getLocalData().getEnvironment().equals(Global.Environment.PREVIEW)?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);
}
