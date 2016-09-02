package com.kaurihealth.datalib.remote;

import android.support.annotation.NonNull;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.utilslib.constant.Global;

import retrofit2.Retrofit;

/**
 * Created by jianghw on 2016/8/18.
 * <p/> Retrofit生产工厂 单利
 * 描述：
 */
public class RetrofitFactory {
    private String environment = null;
    private String baseUrl = null;

    private static class SingletonHolder {
        private static final RetrofitFactory INSTANCE = new RetrofitFactory();
}

    public static RetrofitFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取Environment 信息
     *
     * @return
     */
    private String getCurEnvironment() {
        if (environment != null) return environment;
        environment = LocalData.getLocalData().getEnvironment();
        return environment;
    }

    @NonNull
    private String getBaseUrl() {
        if (baseUrl != null) return baseUrl;
        switch (getCurEnvironment()) {
            case Global.Environment.DEVELOP:
                baseUrl = Global.Url.DEVELOP_URL;
                break;
            case Global.Environment.TEST:
                baseUrl = Global.Url.TEST_URL;
                break;
            case Global.Environment.PREVIEW:
                baseUrl = Global.Url.PREVIEW_URL;
                break;
            default:
                baseUrl = Global.Url.PREVIEW_URL;
                break;
        }
        return baseUrl;
    }

    public Retrofit createRetrofit(String requirements) {
        switch (requirements) {
            case Global.Factory.NO_TOKEN:
                return NoTokenRetrofit.getInstance().createRetrofit(getBaseUrl());
            case Global.Factory.NO_TOKEN_EXECUTE:
                return new NoTokenExecuteRetrofit().createRetrofit(getBaseUrl());
            case Global.Factory.DEFAULT_TOKEN:
                return DefaultRetrofit.getInstance().createRetrofit(getBaseUrl());
            default:
                return DefaultRetrofit.getInstance().createRetrofit(getBaseUrl());
        }
    }
}
