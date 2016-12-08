package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/9.
 */
public class EditPrescriptionpresenter<V> implements IEditPrescriptionPresenter<V>{
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IEditPrescriptionView mActivity;

    @Inject
    public EditPrescriptionpresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();

    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IEditPrescriptionView) view;
    }

    @Override
    public void onSubscribe() {
        PrescriptionBean newPrescriptionBean = mActivity.getCurrentNewPrescriptionBean();
        Subscription subscription = mRepository.UpdatePrescription(newPrescriptionBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        mActivity.dataInteractionDialog(); //正在加载中...
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<PrescriptionBean>() {
                    @Override
                    public void onCompleted() {
//                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
//                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(PrescriptionBean prescriptionBean) {

                        mActivity.AddPrescriptionIsSuccessful(prescriptionBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void loadDoctorDetail(){
        if (LocalData.getLocalData().getMyself() == null) {
            Subscription subscription = mRepository.loadDoctorDetail()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
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
