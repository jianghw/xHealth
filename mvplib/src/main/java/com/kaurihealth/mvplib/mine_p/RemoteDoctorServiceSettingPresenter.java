package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 24/08/2016.
 */
public class RemoteDoctorServiceSettingPresenter<V> implements IRemoteDoctorServiceSettingPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IRemoteDoctorServiceSettingView mActivity;

    @Inject
    RemoteDoctorServiceSettingPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IRemoteDoctorServiceSettingView) view;
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
