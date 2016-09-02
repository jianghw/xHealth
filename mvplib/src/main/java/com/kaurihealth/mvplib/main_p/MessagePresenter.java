package com.kaurihealth.mvplib.main_p;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.kaurihealth.datalib.repository.IDataSource;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
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

        Subscription subscription = mRepository.chatAllConversations()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //加载中...
                        mFragment.loadingIndicator(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AVIMConversation>>() {
                    @Override
                    public void onCompleted() {
                        mFragment.loadingIndicator(false);
                        mFirstLoad = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.loadingIndicator(false);
                        mFragment.AllConversationsError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<AVIMConversation> list) {
                        mFragment.AllConversationsSuccess(list);
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
