package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 1/09/2016.
 */
public class DoctorTeamPresenter<V> implements IDoctorTeamPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IDoctorTeamView mFragment;
    private  List<Integer> doctorIds = new ArrayList<>();

    @Inject
    DoctorTeamPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mFragment = (IDoctorTeamView) view;
    }

    @Override
    public void onSubscribe() {
        //TODO  param：int patientId
        int patientRelationshipId = mFragment.getPatientRelationshipId();
        //Subscription subscription = mRepository.LoadDoctorPatientRelationshipForPatientId(patientRelationshipId)  //-->old
        Subscription subscription = mRepository.loadDoctorTeamForPatient(patientRelationshipId)    //返回数据 --> List<DoctorPatientRelationshipBean>
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(/*new Subscriber<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<DoctorPatientRelationshipBean> doctorPatientRelationshipBeen) {
                        mFragment.lazyLoadingDataSuccess(doctorPatientRelationshipBeen);
                    }
                }*/
                        new Action1<List<DoctorPatientRelationshipBean>>() {  //当服务器返回数据的时候，拿到数据调用
                            @Override
                            public void call(List<DoctorPatientRelationshipBean> beanList) {    //相当于onNext()

                                mFragment.lazyLoadingDataSuccess(beanList);  //将服务器的数据传数据回view层
                            }

                        },
                        new Action1<Throwable>() {   //相当于onError
                            @Override
                            public void call(Throwable throwable) {
                                loadingRemoteData(false);
                                mFragment.showToast(throwable.getMessage());
                            }
                        }

                );

        mSubscriptions.add(subscription);
    }



    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment = null;
    }


    //访问远程数据
    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty){   //刷新
            mRepository.manuallyRefresh();  //手动刷新
        }
        onSubscribe();
    }


    //读取和自己是协作
    @Override
    public void showCooperationDoctor() {
        Subscription subscription = mRepository.loadDoctorDetail()
                .subscribeOn(Schedulers.io())
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
                        int doctorId = doctorDisplayBean.getDoctorId();
                    }
                });
        mSubscriptions.add(subscription);
    }


}
