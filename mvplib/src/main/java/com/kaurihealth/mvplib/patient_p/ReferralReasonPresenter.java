package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
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
    public ReferralReasonPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IReferralReasonView) view;
    }

    /**
     * 向多个医生转诊患者
     */
    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.InsertPatientRequestReferralByDoctorList(
                mActivity.getPatientRequestReferralDoctorDisplayBean())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseDisplayBean>() {
                    @Override
                    public void call(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
                            mActivity.insertPatientRequestReferralByDoctorListSucceed(bean);
                        } else {
                            mActivity.displayErrorDialog(bean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 向一个医生转诊多个患者
     */
    public void InsertPatientRequestReferralByPatientList() {
        Subscription subscription = mRepository.InsertPatientRequestReferralByPatientList(mActivity.getPatientRequestBean())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseDisplayBean>() {
                    @Override
                    public void call(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
                            mActivity.insertPatientRequestReferralByDoctorListSucceed(bean);
                        } else {
                            mActivity.displayErrorDialog(bean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.dismissInteractionDialog();
        mSubscriptions.clear();
        mActivity = null;
    }
}
