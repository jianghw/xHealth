package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/12/1.
 * <p/>
 * Describe:
 */

public class ItemDetailsPresenter<V> implements IItemDetailsPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IItemDetailsView activity;

    @Inject
    public ItemDetailsPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        activity = (IItemDetailsView) view;
    }

    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.LoadDoctorDetailByDoctorId(activity.getDoctorOrPatientId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> activity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        activity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.dismissInteractionDialog();
                        activity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean bean) {
                        DoctorRelationshipBean doctorRelationshipBean = new DoctorRelationshipBean();
                        doctorRelationshipBean.setRelatedDoctor(bean);
                        activity.getResult(doctorRelationshipBean, "Doctor");
                        LogUtils.jsonDate(doctorRelationshipBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void loadDoctorPatientRelationshipForPatientId() {
        Subscription subscription = mRepository.loadDoctorPatientRelationshipForPatientId(activity.getDoctorOrPatientId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> activity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                        activity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.dismissInteractionDialog();
                        activity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorPatientRelationshipBean> relationshipBean) {
                        LogUtils.jsonDate(relationshipBean);

                        for (DoctorPatientRelationshipBean bean : relationshipBean) {
                            boolean isActive = DateUtils.isActive(bean.isIsActive(), bean.getEndDate());
                            if (isActive){
                                activity.getResult(bean, "Patient");
                            }
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
