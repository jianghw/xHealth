package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.mvplib.main_p.IPatientRequestPresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/21.
 */
public class PatientRequesetInfoPresenter<V> implements IPatientRequestPresenter<V>{

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IPatientRequestInfoView mActivity;

    @Inject
    public PatientRequesetInfoPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IPatientRequestInfoView) view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
    mSubscriptions.clear();
    }

    @Override
    public void loadDoctorDetail(boolean b) {

    }
}
