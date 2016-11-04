package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 22/09/2016.
 */
public class FamilyMembersPresenter<V> implements IFamilyMembersPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IFamilyMembersView mActivity;

    @Inject
    FamilyMembersPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IFamilyMembersView) view;
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
