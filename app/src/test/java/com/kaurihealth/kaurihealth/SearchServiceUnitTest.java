package com.kaurihealth.kaurihealth;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.request_bean.bean.SearchResultBean;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.service.ISearchService;
import com.kaurihealth.datalib.request_bean.builder.InitialiseSearchRequestBeanBuilder;
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
 * Created by Nick on 17/05/2016.
 */


public class SearchServiceUnitTest extends TestBase {
    private TokenBean tokenBean;
    private ISearchService searchService;
    @Before
    public void SetUp() {
        tokenBean = super.LoginAsDoctor("18550000723" , "11111111");
        searchService = new ServiceFactory(Url.prefix,tokenBean.accessToken).getSearchService();
    }
    @Test
    public void CanSearch() throws InterruptedException {
        final CountDownLatch signal = new CountDownLatch(1);
        InitialiseSearchRequestBean initialiseSearchRequestBean = new InitialiseSearchRequestBeanBuilder().Build("default" , "aa" );
        Call<SearchResultBean> call = searchService.KeywordSearch_out(initialiseSearchRequestBean);
        call.enqueue(new Callback<SearchResultBean>() {
            @Override
            public void onResponse(Call<SearchResultBean> call, Response<SearchResultBean> response) {
                if (response.isSuccessful()) {
                    Assert.assertEquals(response.body().status , "OK");
                } else {
                    Assert.fail(response.message());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<SearchResultBean> call, Throwable t) {
                Assert.fail(t.getMessage());
                signal.countDown();
            }
        });
        signal.await(30, TimeUnit.SECONDS);
    }
}
