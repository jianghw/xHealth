package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/29.
 */
public class BlankDoctorPresenter<T>  implements  IBlankDoctorPresenter <T>{

    private  final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IBlankDoctorView mActivity;
    @Inject
    public  BlankDoctorPresenter(IDataSource repository){
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(T view) {
        mActivity = (IBlankDoctorView)view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;

    }

    @Override
    public void RemoveDoctorRelationship() {

    }
}
