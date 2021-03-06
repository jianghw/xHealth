package com.kaurihealth.mvplib.rcord_details_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordCountDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 */
public class NMedicalRecordsPresenter<V> implements INMedicalRecordsPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private INMedicalRecordsView mFragment;
    private boolean mFirstLoad = true;

    @Inject
    NMedicalRecordsPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (INMedicalRecordsView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(false);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }
        LoadNewPatientRecordCountsByPatientId();
    }

    /**
     * 病理详情api
     */
    @Override
    public void LoadNewPatientRecordCountsByPatientId() {
        int patientId = mFragment.getPatientId();
        Subscription subscription = mRepository.loadNewPatientRecordCountsByPatientId(patientId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mFragment.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<PatientRecordCountDisplayBean>() {
                            @Override
                            public void onCompleted() {
                                mFirstLoad = false;
                            }

                            @Override
                            public void onError(Throwable e) {
                                mFragment.loadingIndicator(false);
                                mFragment.loadNewPatientRecordCountsByPatientIdError(e.getMessage());
                            }

                            @Override
                            public void onNext(PatientRecordCountDisplayBean bean) {
                                loadDoctorPatientRelationshipForDoctor(bean);
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 医患关系 判断转诊一否
     */
    public void loadDoctorPatientRelationshipForDoctor(PatientRecordCountDisplayBean displayBean) {
        Subscription subscription = mRepository.LoadDoctorPatientRelationshipForDoctor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void call(List<DoctorPatientRelationshipBean> beanList) {
                        mFragment.loadingIndicator(false);
                        relationshipBetweenTag(displayBean, beanList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                        mFragment.loadingIndicator(false);
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void relationshipBetweenTag(PatientRecordCountDisplayBean bean, List<DoctorPatientRelationshipBean> beenList) {
        if (beenList != null) {
            for (DoctorPatientRelationshipBean doctorPatientRelationshipBean : beenList) {
                if (doctorPatientRelationshipBean.getPatient().getPatientId() == bean.getPatient().getPatientId()) {
                    bean.isFriend = true;
                    break;
                }
            }
        }
        mFragment.loadNewPatientRecordCountsByPatientId(bean);
    }

    public void insertNewRelationshipByDoctor(int mPatientId) {
        Subscription subscription = mRepository.insertNewRelationshipByDoctor(mPatientId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mFragment.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorPatientRelationshipBean>() {
                    @Override
                    public void onCompleted() {
                        mFragment.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorPatientRelationshipBean bean) {
                        mFragment.insertPatientSucceed(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mFragment != null) mFragment.loadingIndicator(false);
        mSubscriptions.clear();
        mFragment = null;
    }
}
