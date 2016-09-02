package com.kaurihealth.mvplib.department_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/31.
 */
public class SelectDepartmentLevel1Presenter<V> implements ISelectDepartmentLevel1Presenter<V> {
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private ISelectDepartmentLevel1Presenter mActivity;


    @Inject
    SelectDepartmentLevel1Presenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }


    @Override
    public void setPresenter(V view) {
        mActivity = (ISelectDepartmentLevel1Presenter) view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
