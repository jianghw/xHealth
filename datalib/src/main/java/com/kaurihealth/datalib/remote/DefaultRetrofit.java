package com.kaurihealth.datalib.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.request_bean.bean.RefreshBean;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jianghw on 2016/8/18.
 * <p/>
 * 描述：
 */
public class DefaultRetrofit implements IRetrofit {

    private final OkHttpClient client;

    private static class SingletonHolder {
        private static final DefaultRetrofit INSTANCE = new DefaultRetrofit();
    }

    public static DefaultRetrofit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public DefaultRetrofit() {
        client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(mTokenInterceptor)
                .authenticator(mAuthenticator)
                .build();
    }

    @Override
    public Retrofit createRetrofit(String url) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    //OkHttpClient并设置超时时间
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.d("OKHttp", message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);
    //打印信息

    /**
     * 拦截 Token添加器
     */
    Interceptor mTokenInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String token = LocalData.getLocalData().getTokenBean().getAccessToken();
            LogUtils.d(token);
            //需要token
            Request authorised = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(authorised);
        }
    };

    /**
     * Token 拦截刷新器
     */
    Authenticator mAuthenticator = new Authenticator() {
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            TokenBean refreshToken = refreshTokenBean();
            return response.request().newBuilder()
                    .header("Authorization", "Bearer " + refreshToken.getAccessToken())
                    .build();
        }
    };

    /**
     * 刷新Token
     *
     * @return
     * @throws IOException
     */
    @NonNull
    private TokenBean refreshTokenBean() throws IOException {
        RefreshBean refreshBean = LocalData.getLocalData().refreshTokenBean();
        //同步获取刷新token
        //FIXME shared
        Call<TokenBean> refresh = RetrofitFactory.getInstance()
                .createRetrofit(Global.Factory.NO_TOKEN_EXECUTE)
                .create(ILoginService.class).Refresh(refreshBean);
        retrofit2.Response<TokenBean> newResponse = refresh.execute();
        TokenBean tokenBean = newResponse.body();

        LogUtils.json(new Gson().toJson(tokenBean));
        LocalData.getLocalData().insertEnsureByOne(tokenBean);
        LocalData.getLocalData().setTokenBean(tokenBean);
        return tokenBean;
    }

}
