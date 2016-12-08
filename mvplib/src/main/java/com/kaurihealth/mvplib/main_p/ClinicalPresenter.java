package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 23/08/2016.
 */
public class ClinicalPresenter<V> implements IClinicalPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IClinicalView mFragment;

    @Inject
    ClinicalPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mFragment = (IClinicalView) view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {

    }
}
