package com.kaurihealth.kaurihealth.Builder;

import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.datalib.request_bean.bean.NewRegisterBean;

/**
 * Created by Nick on 23/04/2016.
 */
public class NewRegisterBeanBuilder {
    public NewRegisterBean Build(String phoneNumber) {
        NewRegisterBean newRegisterBean = new NewRegisterBean();
        newRegisterBean.userName = phoneNumber;
        newRegisterBean.firstName = "Android";
        newRegisterBean.lastName = "Test";
        newRegisterBean.textVerifyCode = "";
        newRegisterBean.gender = "男";
        newRegisterBean.dateOfBirth = DateUtils.Today();
        newRegisterBean.password = "1234";
        newRegisterBean.userType = "患者";

        return newRegisterBean;
    }
}
