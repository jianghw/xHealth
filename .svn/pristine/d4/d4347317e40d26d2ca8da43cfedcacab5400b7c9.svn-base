package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/30.
 */
public class BlankPatientPresenter <V> implements  IBlankPatientPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscription;
    private IBlankPatientView mFragment;

    @Inject
    public  BlankPatientPresenter(IDataSource repository){
        mRepository = repository;
        mSubscription = new CompositeSubscription();
    }


    @Override
    public void setPresenter(V view) {
        mFragment = (IBlankPatientView) view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
