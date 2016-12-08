package com.kaurihealth.datalib.service;

import com.kaurihealth.datalib.response_bean.NotifyIsReadDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.UserNotifyDisplayBean;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jianghw on 2016/11/14.
 * <p/>
 * Describe:
 */

public interface INotifyService {
    /**
     * 查询该用户的所有系统消息
     *
     * @return
     */
    @GET("api/Notify/LoadUserAllNotify")
    Observable<List<UserNotifyDisplayBean>> loadUserAllNotify();

    /**
     * App和Web端 判断小红点是否显示时触发
     *
     * @return
     */
    @GET("api/Notify/IsRead")
    Observable<NotifyIsReadDisplayBean> isReadNotify();

    /**
     * 更新 已读 未读
     */
    @POST("api/Notify/UpdateUserNotifyIsRead")
    Observable<ResponseDisplayBean> updateUserNotifyIsRead(@Body List<Integer> bean);

    /**
     * Web端和app 点击 “全部忽略”时触发
     *
     * @return
     */
    @POST("api/Notify/UpdateUserNotify")
    Observable<ResponseDisplayBean> updateUserNotify();
}
