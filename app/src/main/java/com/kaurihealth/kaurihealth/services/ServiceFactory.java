package com.kaurihealth.kaurihealth.services;

import android.content.Context;

import com.example.commonlibrary.widget.bean.TokenBean;
import com.kaurihealth.datalib.service.IChangePasswordService;
import com.kaurihealth.datalib.service.ICheckVersionService;
import com.kaurihealth.datalib.service.ICreditTransactionService;
import com.kaurihealth.datalib.service.IDepartmentService;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.IDoctorRelationshipService;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.datalib.service.IHealthConditionService;
import com.kaurihealth.datalib.service.IHospitalsService;
import com.kaurihealth.datalib.service.ILabTestService;
import com.kaurihealth.datalib.service.ILiteratureCommentService;
import com.kaurihealth.datalib.service.ILiteratureReplyService;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.datalib.service.IMedicalLiteratureLikeService;
import com.kaurihealth.datalib.service.IMedicalLiteratureService;
import com.kaurihealth.datalib.service.IPathologyRecordService;
import com.kaurihealth.datalib.service.IPatientRecordService;
import com.kaurihealth.datalib.service.IPatientRequestService;
import com.kaurihealth.datalib.service.IPatientService;
import com.kaurihealth.datalib.service.IPrescriptionService;
import com.kaurihealth.datalib.service.IPriceService;
import com.kaurihealth.datalib.service.IRecordService;
import com.kaurihealth.datalib.service.IRegisterService;
import com.kaurihealth.datalib.service.ISearchService;
import com.kaurihealth.datalib.service.ISupplementaryTestService;
import com.kaurihealth.datalib.service.IUserCashOutAccountService;
import com.kaurihealth.datalib.request_bean.bean.RefreshBean;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.kaurihealth.kaurihealth.util.Interface.IPutter;
import com.kaurihealth.kaurihealth.util.Putter;
import com.kaurihealth.kaurihealth.util.Url;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Nick on 21/04/2016.
 */
public class ServiceFactory implements IServiceFactory {
    private Retrofit retrofit;
    private ILoginService loginService;
    private ITokenRefreshService tokenRefreshService;

    public ServiceFactory(String apiEndpoint) {
        retrofit = new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public ServiceFactory(String apiEndpoint, IPutter putter, final TokenBean tokenBean) {
        loginService = getLoginService(apiEndpoint);
        tokenRefreshService = new TokenRefreshService(loginService ,putter);
        final TokenBean newTokenBean = tokenRefreshService.refreshToken(tokenBean);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + newTokenBean.accessToken)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    public ServiceFactory(String apiEndpoint, final Context context) {
        final IGetter getter = Getter.getInstance(context);
        final String accessToken = getter.getToken();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + accessToken)
                        .method(original.method(), original.body())
                        .build();
                Response proceed = chain.proceed(request);
                if (proceed.code() == 401) {
                    RefreshBean refreshBean = new RefreshBean();
                    try {
                        TokenBean tokenBean = getter.getTokenBean();
                        refreshBean.refreshToken = tokenBean.refreshToken;
                        refreshBean.userId = tokenBean.user.userId;
                        refreshBean.deviceId = "android";
                        Call<TokenBean> refresh = null;
                        retrofit2.Response<TokenBean> response = refresh.execute();
                        if (response.isSuccessful()) {
                            IPutter putter = new Putter(context);
                            putter.setTokenBean(response.body());
                            request = original.newBuilder()
                                    .header("Authorization", "Bearer " + response.body().accessToken)
                                    .method(original.method(), original.body())
                                    .build();
                            proceed = chain.proceed(request);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                return proceed;
            }
        });
        OkHttpClient client = httpClient.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    //For Test
    @Deprecated
    public ServiceFactory(String apiEndpoint, final String accessToken) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", "Bearer " + accessToken)
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
    }

    private ILoginService createRefresh(String apiEndpoint) {
        if (loginService == null) {
            Retrofit retrofitRefresh = new Retrofit.Builder().
                    baseUrl(apiEndpoint).
                    addConverterFactory(JacksonConverterFactory.create()).
                    build();
            loginService = retrofitRefresh.create(ILoginService.class);
        }
        return loginService;
    }

    @Override
    public ILoginService getLoginService(String apiEndpoint) {
        Retrofit retrofitRefresh = new Retrofit.Builder().
                baseUrl(apiEndpoint).
                addConverterFactory(JacksonConverterFactory.create()).
                build();
        return retrofitRefresh.create(ILoginService.class);
    }

    @Override
    public IHospitalsService getLoadAllHospitalsService() {
        return retrofit.create(IHospitalsService.class);
    }

    @Override
    public IPriceService getUpdateDoctorProductPrice() {
        return retrofit.create(IPriceService.class);
    }

    @Override
    public ICreditTransactionService getCreditTransactions() {
        return retrofit.create(ICreditTransactionService.class);
    }

    @Override
    public IRecordService getAddNewPatientRecord() {
        return retrofit.create(IRecordService.class);
    }

    @Override
    public IPrescriptionService getInsertPrescription() {
        return retrofit.create(IPrescriptionService.class);
    }


    @Override
    public ICheckVersionService getCheckVersionService() {
        return retrofit.create(ICheckVersionService.class);
    }

    @Override
    public IPatientService getPatientService() {
        return retrofit.create(IPatientService.class);
    }

    @Override
    public IDoctorPatientRelationshipService getDoctorPatientRelationshipService() {
        return retrofit.create(IDoctorPatientRelationshipService.class);
    }

    @Override
    public IHealthConditionService getHealthConditionService() {
        return retrofit.create(IHealthConditionService.class);
    }

    @Override
    public IPrescriptionService getPrescriptionService() {
        return retrofit.create(IPrescriptionService.class);
    }

    @Override
    public IPatientRequestService getPatientRequestService() {
        return retrofit.create(IPatientRequestService.class);
    }

    @Override
    public IRegisterService getRegisterService() {
        return retrofit.create(IRegisterService.class);
    }

    @Override
    public IDoctorService getDoctorService() {
        return retrofit.create(IDoctorService.class);
    }

    @Override
    public IDepartmentService getDepartmentService() {
        return retrofit.create(IDepartmentService.class);
    }

    @Override
    public IChangePasswordService getChangePasswordService() {
        return retrofit.create(IChangePasswordService.class);
    }

    @Override
    public IPriceService getPriceService() {
        return retrofit.create(IPriceService.class);
    }


    @Override
    public ISearchService getSearchService() {
        return retrofit.create(ISearchService.class);
    }

    @Override
    public IPatientRecordService getPatientRecordService() {
        return retrofit.create(IPatientRecordService.class);
    }

    @Override
    public IRecordService getIRecordService() {
        return retrofit.create(IRecordService.class);
    }

    @Override
    public ILabTestService getILabTestService() {
        return retrofit.create(ILabTestService.class);
    }

    @Override
    public IPathologyRecordService getIPathologyRecordService() {
        return retrofit.create(IPathologyRecordService.class);
    }

    @Override
    public ISupplementaryTestService getISupplementaryTestService() {
        return retrofit.create(ISupplementaryTestService.class);
    }

    @Override
    public IMedicalLiteratureService getMedicalLiteratureService() {
        return retrofit.create(IMedicalLiteratureService.class);
    }

    @Override
    public IMedicalLiteratureLikeService getMedicalLiteratureLikeService() {
        return retrofit.create(IMedicalLiteratureLikeService.class);
    }

    @Override
    public ILiteratureReplyService getLiteratureReplyService() {
        return retrofit.create(ILiteratureReplyService.class);
    }

    @Override
    public ILiteratureCommentService getILiteratureCommentService() {
        return retrofit.create(ILiteratureCommentService.class);
    }

    @Override
    public IUserCashOutAccountService getIUserCashOutAccountService() {
        return retrofit.create(IUserCashOutAccountService.class);
    }

    @Override
    public IDoctorRelationshipService getIDoctorRelationshipService() {
        return retrofit.create(IDoctorRelationshipService.class);
    }

}
