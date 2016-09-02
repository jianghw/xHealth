package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.service.IDoctorService;
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
 * Created by Nick on 24/04/2016.
 */
public class DoctorServiceUnitTest extends TestBase {
    private TokenBean tokenBean;
    private IDoctorService doctorService;
    @Before
    public void SetUp() {
        tokenBean = super.LoginAsDoctor("18550000723" , "11111111");
        doctorService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getDoctorService();
    }

    @Test
    public void CanLoadDoctorDetails() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);

        Call<DoctorDisplayBean> listCall = doctorService.LoadDoctorDetail_out();
        listCall.enqueue(new Callback<DoctorDisplayBean>() {
            @Override
            public void onResponse(Call<DoctorDisplayBean> call, Response<DoctorDisplayBean> response) {
                if (response.isSuccessful()) {
                    Assert.assertNotNull(response.body());
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<DoctorDisplayBean> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }
}
