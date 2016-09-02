package com.kaurihealth.datalib.request_bean.bean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/9.
 * 备注：刷新指定的登录用户
 */
public class RefreshBean {
    /**
     * 刷新令牌
     */
    public String refreshToken;
    /**
     * 用户ID
     */
    public int userId;
    /**
     * 设备ID
     */
    public String deviceId;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
