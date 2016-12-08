package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;

/**
 * Created by Nick on 4/07/2016.
 */
public class ResetPasswordDisplayBeanBuilder {
    public ResetPasswordDisplayBean Build(String userName , String newPassword , String hashCode) {
        ResetPasswordDisplayBean resetPasswordDisplayBean = new ResetPasswordDisplayBean();
        resetPasswordDisplayBean.userName = userName;
        resetPasswordDisplayBean.newPassword = newPassword;
        resetPasswordDisplayBean.hashCode = hashCode;

        return resetPasswordDisplayBean;
    }
}
