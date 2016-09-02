package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
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
 * Created by Nick on 24/04/2016.
 */
public class DoctorPatientRelationshipServiceUnitTest extends TestBase {
    private TokenBean tokenBean;
    private IDoctorPatientRelationshipService doctorPatientRelationshipService;
    @Before
    public void SetUp() {
        tokenBean = super.LoginAsDoctor("18550000723" , "11111111");
        doctorPatientRelationshipService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getDoctorPatientRelationshipService();
    }

    @Test
    public void CanLoadDoctorPatientRelationshipUsingDoctorAccount() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        Call<List<DoctorPatientRelationshipBean>> listCall = doctorPatientRelationshipService.LoadDoctorPatientRelationshipForDoctor();
        listCall.enqueue(new Callback<List<DoctorPatientRelationshipBean>>() {
            @Override
            public void onResponse(Call<List<DoctorPatientRelationshipBean>> call, Response<List<DoctorPatientRelationshipBean>> response) {
                if (response.isSuccessful()) {
                    Assert.assertTrue(response.body().size() >= 0);
                }
                else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<DoctorPatientRelationshipBean>> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }
}
