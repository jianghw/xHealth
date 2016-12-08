package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 1/09/2016.
 */
public class PrescriptionPresenter<V> implements IPrescriptionPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IPrescriptionView mActivity;

    @Inject
    PrescriptionPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IPrescriptionView) view;
    }

    @Override
    public void onSubscribe() {
        int patientId = mActivity.getPatientId();
        Subscription subscription = mRepository.loadPrescriptionsByPatientId(patientId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PrescriptionBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PrescriptionBean> prescriptions) {
                        mActivity.getPresctiptionBeanList(prescriptions);
                    }
                });

        mSubscriptions.add(subscription);
    }

    public void loadDoctorDetail(){
        if (LocalData.getLocalData().getMyself() == null) {
            Subscription subscription = mRepository.loadDoctorDetail()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> {
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DoctorDisplayBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(DoctorDisplayBean doctorDisplayBean) {
                            LocalData.getLocalData().setMyself(doctorDisplayBean);
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
