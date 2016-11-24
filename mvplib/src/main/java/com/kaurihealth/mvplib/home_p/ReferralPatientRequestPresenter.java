package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/10/24.
 */

public class ReferralPatientRequestPresenter<V> implements IReferralPatientRequestPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IReferralPatientRequestView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    public ReferralPatientRequestPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IReferralPatientRequestView) view;
    }

    @Override
    public void onSubscribe() {//通过医生查询转诊状态为等待的请求
        if (mFirstLoad) loadReferralDetail(false);
    }

    /**
     * 通过医生查询转诊状态为等待的请求
     */
    @Override
    public void loadReferralDetail(boolean b) {
        Subscription subscription = mRepository.LoadReferralsPatientRequestByDoctorId()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRequestDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                        mFirstLoad = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PatientRequestDisplayBean> requestDisplayBeanList) {
                        mActivity.getPatientRequestDisplayBeanList(requestDisplayBeanList);
                    }
                });
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
