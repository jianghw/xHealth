package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.kaurihealth.services.AuthenticationService;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by Nick on 21/04/2016.
 */
public class TestBase {
    public TokenBean LoginAsDoctor(String userName , String password) {

        LoginBean loginBean = new LoginBean();
        loginBean.userName = userName;
        loginBean.password = password;
        loginBean.deviceId = "Test";

        AuthenticationService authenticationService = new AuthenticationService();
        ILoginService loginService1 = authenticationService.getLoginService();
        Call<TokenBean> tokenBeanCall = null;
        try {
            TokenBean tokenBean = tokenBeanCall.execute().body();
            return tokenBean;
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
            return new TokenBean();
        }
    }

    public TokenBean LoginAsPatient() {

        LoginBean loginBean = new LoginBean();
        loginBean.userName = "18221987046";
        loginBean.password = "123456789";
        loginBean.deviceId = "Test";

        AuthenticationService authenticationService = new AuthenticationService();
        ILoginService loginService1 = authenticationService.getLoginService();
        Call<TokenBean> tokenBeanCall = null;
        try {
            TokenBean tokenBean = tokenBeanCall.execute().body();
            return tokenBean;
        } catch (IOException ex) {
            System.out.print(ex.getMessage());
            return new TokenBean();
        }
    }

}
