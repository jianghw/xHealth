package com.kaurihealth.mvplib.rcord_details_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordCountDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
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
                .filter(new Func1<List<DoctorPatientRelationshipBean>, Boolean>() {
                    @Override
                    public Boolean call(List<DoctorPatientRelationshipBean> been) {
                        return been != null;
                    }
                })
                .flatMap(new Func1<List<DoctorPatientRelationshipBean>, Observable<PatientRecordCountDisplayBean>>() {
                    @Override
                    public Observable<PatientRecordCountDisplayBean> call(List<DoctorPatientRelationshipBean> been) {
                        return Observable.from(been)
                                .map(new Func1<DoctorPatientRelationshipBean, PatientDisplayBean>() {
                                    @Override
                                    public PatientDisplayBean call(DoctorPatientRelationshipBean bean) {
                                        return bean.getPatient();
                                    }
                                })
                                .filter(new Func1<PatientDisplayBean, Boolean>() {
                                    @Override
                                    public Boolean call(PatientDisplayBean bean) {
                                        return null != bean;
                                    }
                                })
                                .map(new Func1<PatientDisplayBean, Integer>() {
                                    @Override
                                    public Integer call(PatientDisplayBean bean) {
                                        return bean.getPatientId();
                                    }
                                })
                                .map(new Func1<Integer, PatientRecordCountDisplayBean>() {
                                    @Override
                                    public PatientRecordCountDisplayBean call(Integer integer) {
                                        if (integer == displayBean.getPatient().getPatientId())
                                            displayBean.isFriend = true;
                                        return displayBean;
                                    }
                                });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PatientRecordCountDisplayBean>() {
                               @Override
                               public void call(PatientRecordCountDisplayBean bean) {
                                   mFragment.loadNewPatientRecordCountsByPatientId(bean);
                                   mFragment.loadingIndicator(false);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable e) {
                                mFragment.loadingIndicator(false);
                                mFragment.loadNewPatientRecordCountsByPatientIdError(e.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
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
