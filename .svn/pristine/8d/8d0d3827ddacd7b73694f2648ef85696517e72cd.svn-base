package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewNotifyConfigBean;
import com.kaurihealth.datalib.response_bean.NotifyConfigDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public class SystemMessageSettingPresenter<V> implements ISystemMessageSettingPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ISystemMessageSettingView mActivity;

    @Inject
    SystemMessageSettingPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ISystemMessageSettingView) view;
    }

    /**
     * Web端和app 点击 “全部忽略”时触发
     */
    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.updateUserNotify()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseDisplayBean>() {
                    @Override
                    public void call(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.updateUserNotifySuccessful();
                            mActivity.dismissInteractionDialog();
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

    /**
     * 查询该用户的系统消息设置
     */
    @Override
    public void loadNotifyConfig() {
        Subscription subscription = mRepository.loadNotifyConfig()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<NotifyConfigDisplayBean>() {
                    @Override
                    public void call(NotifyConfigDisplayBean bean) {
                        mActivity.initSwitchState(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        mActivity.dismissInteractionDialog();
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 更新系统消息设置
     *
     * @param checked
     */
    @Override
    public void updateNotifyConfig(boolean checked) {
        Subscription subscription = mRepository.updateNotifyConfig(new NewNotifyConfigBean(checked))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseDisplayBean>() {
                    @Override
                    public void call(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
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
}
