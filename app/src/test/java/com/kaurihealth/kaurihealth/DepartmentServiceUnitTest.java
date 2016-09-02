package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.service.IDepartmentService;
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
public class DepartmentServiceUnitTest extends TestBase {
    private TokenBean tokenBean;
    private IDepartmentService departmentService;

    @Before
    public void SetUp() {
        tokenBean = super.LoginAsDoctor("18550000723" , "11111111");
        departmentService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getDepartmentService();
    }

    @Test
    public void CanLoadAllDepartment() throws InterruptedException {
        //开始的倒数锁
        final CountDownLatch signal = new CountDownLatch(1);
        //获取数据集合
        Call<List<DepartmentDisplayBean>> listCall = departmentService.LoadAllDepartment();
        //将数据集合进行显示
        listCall.enqueue(new Callback<List<DepartmentDisplayBean>>() {
            @Override
            public void onResponse(Call<List<DepartmentDisplayBean>> call, Response<List<DepartmentDisplayBean>> response) {
                if (response.isSuccessful()) {
                    Assert.assertTrue(response.body().size() >= 0);
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<DepartmentDisplayBean>> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }


}
