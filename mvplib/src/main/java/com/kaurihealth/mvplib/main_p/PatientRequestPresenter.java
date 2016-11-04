package com.kaurihealth.mvplib.main_p;

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
 * Created by mip on 2016/9/18.
 * <p/>
 * 描述：
 */
public class PatientRequestPresenter<V> implements IPatientRequestPresenter<V> {
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IPatientRequestView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    PatientRequestPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IPatientRequestView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadDoctorDetail(false);
    }

    @Override
    public void loadDoctorDetail(boolean b) {
        //查看病人发过来的请求
        Subscription subscription = mRepository.LoadPatientRequestsByDoctor()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRequestDisplayBean>>() {
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
                    public void onNext(List<PatientRequestDisplayBean> patientRequestDisplayBeen) {
                        mActivity.refreshListView(patientRequestDisplayBeen);
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
