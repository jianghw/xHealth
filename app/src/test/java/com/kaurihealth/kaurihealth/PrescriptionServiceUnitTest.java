package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.service.IPrescriptionService;
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
 * Created by Nick on 22/04/2016.
 */
public class PrescriptionServiceUnitTest extends  TestBase{
    private TokenBean tokenBean;
    private IPrescriptionService prescriptionService;
    @Before
    public void SetUp() {
        tokenBean = super.LoginAsPatient();
        prescriptionService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getPrescriptionService();
    }

    @Test
    public void CanLoadPatientPrescription() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        Call<List<PrescriptionBean>> listCall = prescriptionService.LoadPrescriptionsByPatientId(108123);
        listCall.enqueue(new Callback<List<PrescriptionBean>>() {
            @Override
            public void onResponse(Call<List<PrescriptionBean>> call, Response<List<PrescriptionBean>> response) {
                if (response.isSuccessful()) {
                    Assert.assertTrue(response.body().size() >= 0);
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<PrescriptionBean>> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }
}
