package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.request_bean.bean.NewNotifyConfigBean;
import com.kaurihealth.datalib.response_bean.NotifyConfigDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2016/11/14.
 * <p/>
 * Describe:
 */

public interface INotifyConfigService {
    /**
     * 查询该用户的系统消息设置
     *
     * @return
     */
    @GET("api/NotifyConfig/LoadNotifyConfig")
    Observable<NotifyConfigDisplayBean> loadNotifyConfig();

    /**
     * 更新系统消息设置
     * @param newNotifyConfigBean
     * @return
     */
    @POST("api/NotifyConfig/UpdateNotifyConfig")
    Observable<ResponseDisplayBean> updateNotifyConfig(@Body NewNotifyConfigBean newNotifyConfigBean);
}
