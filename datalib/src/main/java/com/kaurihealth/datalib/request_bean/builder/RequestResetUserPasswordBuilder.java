package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayBean;

/**
 * Created by Nick on 4/07/2016.
 */
public class RequestResetUserPasswordBuilder {

    public RequestResetPasswordDisplayBean Build(String userName , String verifyCode ) {
        RequestResetPasswordDisplayBean requestResetPasswordDisplayBean = new RequestResetPasswordDisplayBean();
        requestResetPasswordDisplayBean.userName = userName;
        requestResetPasswordDisplayBean.verifyCode = verifyCode;
        return requestResetPasswordDisplayBean;

    }
}

