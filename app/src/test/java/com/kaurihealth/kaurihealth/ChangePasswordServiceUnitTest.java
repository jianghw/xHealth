package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IChangePasswordService;
import com.kaurihealth.kaurihealth.Builder.NewPasswordDisplayBeanBuilder;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Url;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/4/25.
 * 备注：
 */
public class ChangePasswordServiceUnitTest extends TestBase {

    private TokenBean tokenBean;
    private IChangePasswordService changePasswordService;

    @Before
    public void SetUp() {
        tokenBean = super.LoginAsDoctor("18550000723" , "11111111");
        changePasswordService = new ServiceFactory(Url.prefix, tokenBean.accessToken).getChangePasswordService();
    }

    @Test
    public void CanLoadUpdateUserPassword() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        NewPasswordDisplayBean newPasswordDisplayBean = new NewPasswordDisplayBeanBuilder().Builder("123456789","123456789","123456789");
        Call<ResponseDisplayBean> responseDisplayBean = changePasswordService.UpdateUserPassword_out(newPasswordDisplayBean);
        responseDisplayBean.enqueue(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                if (response.isSuccessful()) {
                    //Assert.assertTrue(response.body().isSuccessful);
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<ResponseDisplayBean> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }


}
