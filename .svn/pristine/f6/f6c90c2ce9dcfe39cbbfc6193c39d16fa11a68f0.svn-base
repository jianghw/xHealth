package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.search_p.IBlankDoctorPresenter;
import com.kaurihealth.utilslib.constant.Global;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：登录逻辑
 */
public class DoctorInfoPresenter<V> implements IBlankDoctorPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IDoctorInfoView mActivity;

    @Inject
    DoctorInfoPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IDoctorInfoView) view;
    }

    /**
     * 创建普通病人关系
     */
    @Override
    public void onSubscribe() {

    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    @Override
    public void RemoveDoctorRelationship() {
        int id = mActivity.getRelationshipId();
        Subscription subscription = mRepository.removeDoctorRelationship(id)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
                            mActivity.switchPageUI(Global.Jump.MainActivity);
                        } else {
                            mActivity.displayErrorDialog(bean.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }
}
