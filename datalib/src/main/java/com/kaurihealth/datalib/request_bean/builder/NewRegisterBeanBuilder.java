package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewRegisterBean;
import com.kaurihealth.utilslib.date.DateUtils;

/**
 * Created by Nick on 23/05/2016.
 */
public class NewRegisterBeanBuilder {
    public NewRegisterBean Build(String userName, String password, String textVerifyCode) {
        NewRegisterBean newRegisterBean = new NewRegisterBean();
        newRegisterBean.userName = userName;
        newRegisterBean.password = password;
        newRegisterBean.textVerifyCode = textVerifyCode;
        newRegisterBean.dateOfBirth = DateUtils.Today();
        newRegisterBean.registerType = "安卓";
        newRegisterBean.firstName = "用户";
        newRegisterBean.lastName = "新";
        newRegisterBean.gender = "男";
        newRegisterBean.userType = "医生";
        return newRegisterBean;
    }
}
