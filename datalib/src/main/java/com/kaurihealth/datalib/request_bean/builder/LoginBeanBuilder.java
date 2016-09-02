package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.LoginBean;

/**
 * Created by Nick on 21/05/2016.
 */
public class LoginBeanBuilder {
    public LoginBean Build(String userName , String password) {
        LoginBean loginBean = new LoginBean();
        loginBean.userName = userName;
        loginBean.password = password;
        loginBean.deviceId = "android";
        return loginBean;
    }
}
