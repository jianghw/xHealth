package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.RefreshBean;

/**
 * Created by Nick on 29/07/2016.
 */
public class RefreshBeanBuilder {
    public RefreshBean Build(String refreshToken , int userId){
        RefreshBean refreshBean = new RefreshBean();
        refreshBean.refreshToken = refreshToken;
        refreshBean.userId = userId;
        refreshBean.deviceId = "android";
        return refreshBean;
    }
}
