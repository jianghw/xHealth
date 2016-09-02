package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;

/**
 * Created by Nick on 5/07/2016.
 */
public class NewPasswordBeanBuilder {
    public NewPasswordDisplayBean Build(String userName, String newPassword, String existPassword){
        NewPasswordDisplayBean newPasswordDisplayBean = new NewPasswordDisplayBean();
        newPasswordDisplayBean.userName = userName;
        newPasswordDisplayBean.newPassword = newPassword;
        newPasswordDisplayBean.existPassword = existPassword;
        return newPasswordDisplayBean;

    }
}
