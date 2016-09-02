package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.service.IPatientRequestService;
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
public class PatientRequestServiceUnitTest extends TestBase {
    private TokenBean tokenBean;
    private IPatientRequestService patientRequestService;
    @Before
    public void SetUp() {
        tokenBean = super.LoginAsDoctor("18550000723" , "11111111");
        patientRequestService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getPatientRequestService();
    }

    @Test
    public void CanLoadPatientPrescription() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        Call<List<PatientRequestDisplayBean>> listCall = patientRequestService.LoadPatientRequestsByDoctor();
        listCall.enqueue(new Callback<List<PatientRequestDisplayBean>>() {
            @Override
            public void onResponse(Call<List<PatientRequestDisplayBean>> call, Response<List<PatientRequestDisplayBean>> response) {
                if (response.isSuccessful()) {
                    Assert.assertTrue(response.body().size() >= 0);
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<PatientRequestDisplayBean>> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }
}
