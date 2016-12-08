package com.kaurihealth.mvplib.welcome_p;


import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
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
     * 判断是否需要自动登陆
     */
    @Override
    public void onSubscribe() {
        Subscription subscription = Observable.create(
                new Observable.OnSubscribe<TokenBean>() {
                    @Override
                    public void call(Subscriber<? super TokenBean> subscriber) {
                        try {
                            subscriber.onNext(LocalData.getLocalData().getTokenBean());
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<TokenBean>() {
                            @Override
                            public void call(TokenBean tokenBean) {
                                if (tokenBean == null) return;
                                UserBean userBean = tokenBean.getUser();
                                if (userBean == null) return;
                                if (userBean.getUserType().equals("患者")) {
                                    mActivity.switchPageUI(Global.Jump.LoginActivity);
                                } else if (userBean.getRegistPercentage() < 30) {
                                    mActivity.completeRegister();
                                } else {
                                    loadContactListByDoctorId();
                                    loadDoctorDetail();
                                    mActivity.initChatKitOpen(tokenBean);
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e(throwable.getMessage());
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                mActivity.switchPageUI(Global.Jump.LoginActivity);
                            }
                        });
        mSubscriptions.add(subscription);
    }


    /**
     * 关系列表
     */
    @Override
    public void loadContactListByDoctorId() {
        Subscription subscription = mRepository.loadContactListByDoctorId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<ContactUserDisplayBean>>() {
                            @Override
                            public void call(List<ContactUserDisplayBean> beanList) {

                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mActivity.showToast(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void loadDoctorDetail() {
        Subscription subscription = mRepository.loadDoctorDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<DoctorDisplayBean>() {
                            @Override
                            public void call(DoctorDisplayBean doctorDisplayBean) {
                                LocalData.getLocalData().setMyself(doctorDisplayBean);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mActivity.showToast(throwable.getMessage());
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
