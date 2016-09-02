package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.kaurihealth.services.AuthenticationService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nick on 21/04/2016.
 */
public class AuthenticationServiceUnitTest {
    private ILoginService loginService;
    @Before
    public void SetUp() {
        loginService = new AuthenticationService().getLoginService();
    }

    @Test
    public void CanLogin()  {
        LoginBean loginBean = new LoginBean();
        loginBean.userName = "test01";
        loginBean.password = "Password01";
        loginBean.deviceId = "Test";
        //Login
        Call<TokenBean> tokenBeanCall = null;
        try {
            TokenBean tokenBean = tokenBeanCall.execute().body();
            Assert.assertEquals(loginBean.userName, tokenBean.user.userName);
        }catch (IOException ex) {
            System.out.print(ex.getMessage());
        }
    }

    @Test
    public void CanAsyncLogin() throws InterruptedException {
        LoginBean loginBean = new LoginBean();
        loginBean.userName = "test01";
        loginBean.password = "Password01";
        loginBean.deviceId = "Test";

        final CountDownLatch signal = new CountDownLatch(1);

        Call<TokenBean> tokenBeanCall = null;
        tokenBeanCall.enqueue(new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                if (response.isSuccessful()) {


                }
            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {
                Assert.fail(t.getMessage());
            }
        });

        signal.await(30, TimeUnit.SECONDS);
    }
}
