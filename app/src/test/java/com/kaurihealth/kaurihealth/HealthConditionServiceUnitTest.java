package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.datalib.service.IHealthConditionService;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Url;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Nick on 21/04/2016.
 */
public class HealthConditionServiceUnitTest extends TestBase{
    private TokenBean tokenBean;
    private IHealthConditionService healthConditionService;
    @Before
    public void SetUp() {
        tokenBean = super.LoginAsPatient();
        healthConditionService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getHealthConditionService();
    }
    @Test
    public void CanPatientLoadHealthCondition() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        Call<List<HealthConditionDisplayBean>> listCall = healthConditionService.LoadHealthCondition();
        listCall.enqueue(new Callback<List<HealthConditionDisplayBean>>() {
            @Override
            public void onResponse(Call<List<HealthConditionDisplayBean>> call, Response<List<HealthConditionDisplayBean>> response) {
                if (response.isSuccessful()) {
                    Assert.assertTrue(response.body().size() >= 0);
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<HealthConditionDisplayBean>> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }
}
