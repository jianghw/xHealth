package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;


import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 30/08/2016.
 */
public class EnterPracticeFieldPresenter<V> implements IEnterPracticeFieldPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IEnterPracticeFieldView mActivity;

    @Inject
    EnterPracticeFieldPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IEnterPracticeFieldView) view;
    }

    @Override
    public void onSubscribe() {
        DoctorDisplayBean myself = mActivity.getDoctorDisplayBean();
        //调用repository
        Subscription subscription = mRepository.updateDoctor(myself)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayError(e);
                    }

                    @Override
                    public void onNext(DoctorDisplayBean myself) {
                        mActivity.showToast("修改成功!");
                        mActivity.switchPageUI("");
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
