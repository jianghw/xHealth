package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.service.IPriceService;
import com.kaurihealth.kaurihealth.Builder.NewPriceBeanBuilder;
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
 * 版权所有者：刘正  此单元测试有问题
 * 创建日期： 2016/5/3.
 * 备注：
 */
public class PriceServiceUnitTest extends TestBase {
    private TokenBean tokenBean;
    private IPriceService priceService;

    @Before
    public void SetUp() {
        tokenBean = super.LoginAsDoctor("18550000723" , "11111111");
        priceService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getPriceService();
    }

    @Test
    public void InsertPrice() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        NewPriceBean newPriceBean = new NewPriceBeanBuilder().Build(1001);
        Call<ResponseDisplayBean> listCall = priceService.InsertPrice_out(newPriceBean);
        listCall.enqueue(new Callback<ResponseDisplayBean>() {
            @Override
            public void onResponse(Call<ResponseDisplayBean> call, Response<ResponseDisplayBean> response) {
                if (response.isSuccessful()) {
//                    Assert.assertTrue(response.body().message);
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
