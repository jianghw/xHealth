package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/29.
 */
public class BlankAllPresenter<V> implements  IBlankAllPresenter<V> {
    private  final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private  IBlankAllView mFragment;
    @Inject
    public BlankAllPresenter(IDataSource repository){
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IBlankAllView)view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment = null;
    }
}
