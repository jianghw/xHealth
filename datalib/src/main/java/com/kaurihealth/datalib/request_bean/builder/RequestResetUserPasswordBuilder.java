package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayDto;

/**
 * Created by Nick on 4/07/2016.
 */
public class RequestResetUserPasswordBuilder {

    public RequestResetPasswordDisplayDto Build(String userName , String verifyCode ) {
        RequestResetPasswordDisplayDto requestResetPasswordDisplayDto = new RequestResetPasswordDisplayDto();
        requestResetPasswordDisplayDto.userName = userName;
        requestResetPasswordDisplayDto.verifyCode = verifyCode;
        return requestResetPasswordDisplayDto;

    }
}

