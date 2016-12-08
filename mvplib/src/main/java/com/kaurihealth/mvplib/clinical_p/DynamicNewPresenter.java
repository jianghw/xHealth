package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/11/23.
 */

public class DynamicNewPresenter<V> implements IDynamicNewPresenter<V>{

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IDynamicNewView mActivity;

    @Inject
    public DynamicNewPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        this.mSubscriptions = new CompositeSubscription();

    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IDynamicNewView) view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
