package com.kaurihealth.kaurihealth.Builder;

import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/25.
 * 备注：
 */
public class NewPasswordDisplayBeanBuilder {
    public NewPasswordDisplayBean Builder(String userName, String newPassword , String existPassword ) {
        NewPasswordDisplayBean newPasswordDisplayBean = new NewPasswordDisplayBean();
        newPasswordDisplayBean.userName = userName;
        newPasswordDisplayBean.newPassword = newPassword;
        newPasswordDisplayBean.existPassword = existPassword;
        return newPasswordDisplayBean;
    }

}
