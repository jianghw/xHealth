package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewReferralMessageAlertBean;
import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;

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
public class AddVisitRecordPresenter<V> implements IAddVisitRecordPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IAddVisitRecordView mActivity;

    @Inject
    AddVisitRecordPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IAddVisitRecordView) view;
    }

    /**
     * 根据医患关系ID加载复诊提醒信息
     */
    @Override
    public void onSubscribe() {
        NewReferralMessageAlertBean bean = mActivity.getNewReferralMessageAlertBean();
        if (bean.getCreatTime().getTime() > bean.getReminderTime().getTime()){
            mActivity.displayErrorDialog("复诊时间不能早于当前时间，请重新设置！");
            return;
        }
        Subscription subscription = mRepository.insertReferralMessageAlert(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReferralMessageAlertDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(ReferralMessageAlertDisplayBean bean) {
                        mActivity.insertHealthConditionSucceed(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        if (mActivity != null) mActivity.dismissInteractionDialog();
        mActivity = null;
    }

}
