package com.kaurihealth.mvplib.welcome_p;


import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：Observer观察者 Subscriber
 */
public class WelcomePresenter<V> implements IWelcomePresenter<V> {


    private IWelcomeView mActivity;
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;

    @Inject
    WelcomePresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IWelcomeView) view;
    }

    /**
     * 事件订阅
     */
    @Override
    public void onSubscribe() {
        //判断是否需要自动登陆
        createSubscription();
    }

    @Override
    public void unSubscribe() {

    }

    private void createSubscription() {
        Subscription subscription = Observable
                .create(new Observable.OnSubscribe<Object>() {
                    @Override
                    public void call(Subscriber<? super Object> subscriber) {

                    }
                })
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
        mSubscriptions.add(subscription);
    }
}
