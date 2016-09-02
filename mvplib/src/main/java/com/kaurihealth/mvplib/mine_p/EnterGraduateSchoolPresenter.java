package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 30/08/2016.
 */
public class EnterGraduateSchoolPresenter<V> implements IEnterGraduateSchoolPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IEnterGraduateSchoolView mActivity;

    @Inject
    EnterGraduateSchoolPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IEnterGraduateSchoolView) view;
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
