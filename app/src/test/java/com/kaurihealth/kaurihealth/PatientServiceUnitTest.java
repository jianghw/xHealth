package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.service.IPatientService;
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
 * 版权所有者：刘正
 * 创建日期： 2016/5/3.
 * 备注：
 */
public class PatientServiceUnitTest extends TestBase {
    private TokenBean tokenBean;
    private IPatientService patientService;

    @Before
    public void SetUp() {
        tokenBean = super.LoginAsPatient();
        patientService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getPatientService();
    }

    @Test
    public void SearchPatientByUserName() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        Call<List<PatientDisplayBean>> listCall = null;
        listCall.enqueue(new Callback<List<PatientDisplayBean>>() {
            @Override
            public void onResponse(Call<List<PatientDisplayBean>> call, Response<List<PatientDisplayBean>> response) {
                if (response.isSuccessful()) {
                    Assert.assertTrue(response.body().size() >= 0);
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<PatientDisplayBean>> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }

}
