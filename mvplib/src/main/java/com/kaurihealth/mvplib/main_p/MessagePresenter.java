package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;

import java.util.List;

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
 * 描述：登录逻辑
 */
public class MessagePresenter<V> implements IMessagePresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IMessageView mFragment;
    private boolean mFirstLoad = true;

    @Inject
    MessagePresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IMessageView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(false);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }
        Subscription subscription = mRepository.loadContactListByDoctorId()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mFragment.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<ContactUserDisplayBean>>() {
                            @Override
                            public void call(List<ContactUserDisplayBean> beanList) {
                                mFragment.loadContactListByDoctorIdSucceed();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mFragment.loadingIndicator(false);
                                mFragment.showToast(throwable.getMessage());
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                mFragment.loadingIndicator(false);
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment.loadingIndicator(false);
        mFragment = null;
    }
}
