package com.kaurihealth.kaurihealth.mine.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.DoctorProductPriceBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Config;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Url;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 创建日期:2016/2/19.
 * 介绍：个人信息修改
 */
public class PersonInfoUtil {
    private static PersonInfoUtil instance;
    private static LoadingUtil loadingUtil;
    private Activity activity;
    private Context context;
    private Config.Role role;


    public PersonInfoUtil() {
    }

    public static PersonInfoUtil getInstance(Activity activity, Config.Role role) {
        if (instance == null || instance.activity == null || activity != instance.activity) {
            instance = new PersonInfoUtil();
            instance.activity = activity;
            instance.role = role;

            instance.context = activity.getApplicationContext();
            loadingUtil = LoadingUtil.getInstance(activity);
        }
        return instance;
    }


    /**
     * 专属医生服务设置 远程医疗咨询设置
     */

    public void DoctorSetting(DoctorProductPriceBean bean, final UpdataListenerInterface listener) {
        switch (role) {
            case Doctor:
                new ServiceFactory(Url.prefix, context).getUpdateDoctorProductPrice()
                        .UpdateDoctorProductPrice_out(bean)
                        .enqueue(new Callback<ResponseDisplayBean>() {
                            @Override
                            public void onResponse(Call<ResponseDisplayBean> call, retrofit2.Response<ResponseDisplayBean> response) {
                                if (response.isSuccessful()) {

                                            listener.success();
                                } else {
                                  //  loadingUtil.dismiss("修改失败");
                                    Toast.makeText(context,"修改失败",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
//                                loadingUtil.dismiss(LoadingStatu.NetError.value);
                                Toast.makeText(context, LoadingStatu.NetError.value,Toast.LENGTH_SHORT).show();
                            }
                        });
               // loadingUtil.show();

                /**````````````````````分割线```````````````````````**/
                break;
            case Patient:
                break;
        }
    }

    public  interface UpdataListenerInterface<T> {
        void success();

        void success(T result) throws IOException;

        void error();
    }

    public abstract static class UpdataListener<T> implements UpdataListenerInterface<T> {
        @Override
        public void error() {

        }

        @Override
        public void success(T result) {

        }
    }

    public abstract static class UpdataListenerContent implements UpdataListenerInterface {
        @Override
        public void error() {

        }

        @Override
        public void success() {

        }
    }

    /**
     * 判断str是否为空
     * @param str
     * @return
     */
    public String isempty(String str) {
        if (str == null || str.equals("null")) {
            return "";
        }
        return str;
    }

}
