package com.kaurihealth.kaurihealth.Builder;

import com.kaurihealth.datalib.request_bean.bean.RefreshBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/9.
 * 备注：
 */
public class NewRefreshBeanBuilder {
    public RefreshBean Build(String refreshToken, int userId, String deviceId) {
        RefreshBean refreshBean = new RefreshBean();
        refreshBean.refreshToken = refreshToken;
        refreshBean.userId = userId;
        refreshBean.deviceId = deviceId;
        return refreshBean;
    }
}
