package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.utilslib.AppUtils;

public class InitiateVerificationBeanBuilder {
    public InitiateVerificationBean BuildRegisterInitiateVerification(String phoneNumber) {
        InitiateVerificationBean initiateVerificationBean = new InitiateVerificationBean();
        initiateVerificationBean.phoneNumber = AppUtils.encrypt(phoneNumber);
        initiateVerificationBean.verificationType = "Register";
        return initiateVerificationBean;
    }

    public InitiateVerificationBean BuildResetPasswordInitiateVerification(String phoneNumber) {
        InitiateVerificationBean initiateVerificationBean = new InitiateVerificationBean();
        initiateVerificationBean.phoneNumber = AppUtils.encrypt(phoneNumber);
        initiateVerificationBean.verificationType = "ResetPassword";
        return initiateVerificationBean;
    }
}
