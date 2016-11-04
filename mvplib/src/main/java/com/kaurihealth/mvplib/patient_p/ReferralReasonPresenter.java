package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by KauriHealth on 2016/10/25.
 */

public class ReferralReasonPresenter<V> implements IReferralReasonPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IReferralReasonView mActivity;

    @Inject
    public ReferralReasonPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IReferralReasonView) view;
    }

    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.InsertPatientRequestReferralByDoctorList(mActivity.getBean())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.dismissInteractionDialog();
                        LogUtils.jsonDate(e.toString());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean bean) {
                        mActivity.getresult(bean);
                    }
                });

        mSubscriptions.add(subscription);
    }

    public void InsertPatientRequestReferralByPatientList(){
        Subscription subscription = mRepository.InsertPatientRequestReferralByPatientList(mActivity.getPatientRequestBean())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.dismissInteractionDialog();
                        LogUtils.jsonDate(e.toString());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean bean) {
                        mActivity.getresult(bean);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
