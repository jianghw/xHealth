package com.kaurihealth.kaurihealth.patientwithdoctor.util;

import android.app.Activity;

import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Url;
import com.youyou.zllibrary.httputil.Success;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 张磊 on 2016/4/18.
 * 介绍：
 */
public class FriendNetUtil {
    private LoadingUtil loadingUtil;
    private String messageErrorDefault;
    private String messageSuccessDefault;

    public FriendNetUtil(Activity activity) {
        init(activity, null, null);
    }

    private void init(Activity activity, String messageDefault, String messageSuccessDefault) {
        loadingUtil = LoadingUtil.getInstance(activity);
        this.messageErrorDefault = messageDefault;
        this.messageSuccessDefault = messageSuccessDefault;
    }

    //获取健康记录
    public void getHealthyRecord(int patientId, Activity activity, final Success<Object> listener) {
        init(activity, "获取健康记录失败...", "获取数据成功");
        Map<String, String> parms = new HashMap<>();
        parms.put("patientId", String.valueOf(patientId));

        IServiceFactory serviceFactory = new ServiceFactory(Url.prefix,activity.getApplicationContext());
        serviceFactory
                .getHealthConditionService()
                .LoadHealthConditionByPatientId(parms)
                .enqueue(new Callback<List<HealthConditionDisplayBean>>() {
                    @Override
                    public void onResponse(Call<List<HealthConditionDisplayBean>> call, final retrofit2.Response<List<HealthConditionDisplayBean>> response) {
                        loadingUtil.dismiss(messageSuccessDefault, new LoadingUtil.Success() {
                            @Override
                            public void dismiss() {
                                if (response.isSuccessful()) {
                                    try {
                                        listener.success(response.body());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        loadingUtil.dismiss(messageErrorDefault);
                                    }
                                }
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<HealthConditionDisplayBean>> call, Throwable t) {
                        Bugtags.sendException(t);
                        loadingUtil.dismiss(LoadingStatu.NetError.value);
                    }
                });
        loadingUtil.show();
    }


}
