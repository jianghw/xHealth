package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/29.
 */
public class BlankHospitalPresenter<T> implements  IBlankHospitalPresenter<T> {
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private  IBlankHospitalView  mFragment;
    
    @Inject
    public BlankHospitalPresenter(IDataSource repository){
        mRepository =  repository ;
        mSubscriptions = new CompositeSubscription();
    }


    @Override
    public void setPresenter(T view) {
        mFragment  = (IBlankHospitalView)view;
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
