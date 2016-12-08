package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.UserNotifyDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public class SystemMessagePresenter<V> implements ISystemMessagePresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ISystemMessageView mActivity;

    @Inject
    SystemMessagePresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ISystemMessageView) view;
    }

    /**
     * 根据医患关系ID加载复诊提醒信息
     */
    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.loadUserAllNotify()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<UserNotifyDisplayBean>>() {
                    @Override
                    public void call(List<UserNotifyDisplayBean> beanList) {
                        updateNotifyDataHandle(beanList);
                        loadUserAllNotifySucceed(beanList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void loadUserAllNotifySucceed(List<UserNotifyDisplayBean> list) {
        Subscription subscription = Observable.from(list)
                .filter(new Func1<UserNotifyDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(UserNotifyDisplayBean bean) {
                        return bean != null;
                    }
                })
                .filter(new Func1<UserNotifyDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(UserNotifyDisplayBean bean) {
                        return !bean.isIsDelete();
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<UserNotifyDisplayBean>>() {
                    @Override
                    public void call(List<UserNotifyDisplayBean> beanList) {
                        mActivity.loadUserAllNotifySucceed(beanList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void updateNotifyDataHandle(List<UserNotifyDisplayBean> list) {
        Subscription subscription = Observable.from(list)
                .filter(new Func1<UserNotifyDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(UserNotifyDisplayBean bean) {
                        return bean != null;
                    }
                })
                .filter(new Func1<UserNotifyDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(UserNotifyDisplayBean bean) {
                        return !bean.isIsDelete();
                    }
                })
                .filter(new Func1<UserNotifyDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(UserNotifyDisplayBean bean) {
                        return !bean.isIsRead();
                    }
                })
                .map(new Func1<UserNotifyDisplayBean, Integer>() {
                    @Override
                    public Integer call(UserNotifyDisplayBean bean) {
                        return bean.getUserNotifyId();
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Integer>>() {
                    @Override
                    public void call(List<Integer> list) {
                        updateUserNotify(list);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void updateUserNotify(List<Integer> list) {
        Subscription subscription = mRepository.updateUserNotifyIsRead(list)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
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
                    public void onNext(ResponseDisplayBean bean) {
                        LogUtils.jsonDate(bean);
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
