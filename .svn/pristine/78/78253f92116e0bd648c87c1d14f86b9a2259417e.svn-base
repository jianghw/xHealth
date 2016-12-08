package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/19.
 */
public class DoctorRequestPresenter<V> implements IDoctorRequestPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IDoctorRequestView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    public DoctorRequestPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IDoctorRequestView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadDoctorDetail(false);
    }

    @Override
    public void loadDoctorDetail(boolean b) {
        Subscription subscription = mRepository.LoadPendingDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                        mFirstLoad = false;
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorRelationshipBean> doctorRelationshipBean) {
                        mActivity.getRequestResult(doctorRelationshipBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.loadingIndicator(false);
        mSubscriptions.clear();
        mActivity = null;
    }
}
