package com.kaurihealth.kaurihealth.services;

import com.kaurihealth.datalib.request_bean.bean.ErrorMesBean;

import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by 张磊 on 2016/5/27.
 * 介绍：
 */
public class RetrofitUtil {
    private static <T> Converter<ResponseBody, T> getErrorConverter(Class<T> className, Retrofit retrofit) {
        Converter<ResponseBody, T> errorConverter =
                retrofit.responseBodyConverter(className, new Annotation[0]);
        return errorConverter;
    }

    public static Converter<ResponseBody, ErrorMesBean> getErrorConverter(Retrofit retrofit) {
        Converter<ResponseBody, ErrorMesBean> errorConverter =
                retrofit.responseBodyConverter(ErrorMesBean.class, new Annotation[0]);
        return errorConverter;
    }
}
