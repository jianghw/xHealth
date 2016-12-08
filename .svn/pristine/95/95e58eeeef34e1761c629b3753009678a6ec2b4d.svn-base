package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public class VisitRecordPresenter<V> implements IVisitRecordPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IVisitRecordView mActivity;

    @Inject
    VisitRecordPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IVisitRecordView) view;
    }

    /**
     * 根据医患关系ID加载复诊提醒信息
     */
    @Override
    public void onSubscribe() {
        int doctorPatientShipId = mActivity.getDoctorPatientShipId();
        Subscription subscription = mRepository.loadReferralMessageAlert(doctorPatientShipId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<ReferralMessageAlertDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ReferralMessageAlertDisplayBean> bean) {
                        mActivity.loadHealthConditionSucceed(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    /**
     * 删除复诊提醒信息
     * @param id
     */
    @Override
    public void deleteHealthCondition(int id) {
        Subscription subscription = mRepository.deleteReferralMessageAlert(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReferralMessageAlertDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(ReferralMessageAlertDisplayBean bean) {
                        mActivity.deleteHealthConditionSucceed(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
