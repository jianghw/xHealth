package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.SoftwareInfo;
import com.kaurihealth.mvplib.base_p.Listener;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 23/08/2016.
 * <p/>
 * Describe:
 */
public class MainPresenter<V> implements IMainPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IMainView mFragment;

    @Inject
    MainPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IMainView) view;
    }

    @Override
    public void onSubscribe() {
        String identityToken = mFragment.getPushNotificationDeviceIdentityToken();
        Subscription subscription = mRepository.insertNewPushNotificationDevice(identityToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean bean) {
                        if (!bean.isIsSucess()) mFragment.showToast(bean.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void checkVersion(Map<String, String> map, Listener<SoftwareInfo> listener) {
        Subscription subscription = mRepository.CheckVersion(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SoftwareInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SoftwareInfo softwareInfo) {
                        listener.success(softwareInfo);
                    }
                });
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment = null;
    }
}
