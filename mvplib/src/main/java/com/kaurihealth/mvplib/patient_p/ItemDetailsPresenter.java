package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean bean) {

                        loadDoctorPatientRelationshipForDoctor(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }


    private void loadDoctorPatientRelationshipForDoctor(DoctorDisplayBean bean) {
        Subscription subscription = mRepository.loadAllDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(List<DoctorRelationshipBean> beanList) {
                        //将同意和自己成为协作医生的doctor过滤出来
                        priorityDataArrangement(beanList, bean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 将同意和自己成为协作医生的doctor过滤出来
     */
    private void priorityDataArrangement(List<DoctorRelationshipBean> list, DoctorDisplayBean bean) {
        if (list.isEmpty()) priorityNotReceptDataArrangement(bean);
        if (list.isEmpty()) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<DoctorRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorRelationshipBean doctorRelationshipBean) {
                        return doctorRelationshipBean.getStatus().equals("接受");
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<List<DoctorRelationshipBean>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable throwable) {
                                activity.dismissInteractionDialog();
                                LogUtils.e(throwable.getMessage());
                            }

                            @Override
                            public void onNext(List<DoctorRelationshipBean> doctorRelationshipBeen) {
                                boolean send = false;
                                for (DoctorRelationshipBean relationshipBean : doctorRelationshipBeen) {
                                    if (relationshipBean.getRelatedDoctor().getDoctorId() == bean.getDoctorId()) {
                                        activity.getResult(relationshipBean, "Doctor", true);
                                        send = true;
                                    }
                                }
                                //发送不是医生的协作医生
                                if (!send) priorityNotReceptDataArrangement(bean);
                                //关闭当前的请求
                                if (send) activity.dismissInteractionDialog();
                            }
                        }
                );
        mSubscriptions.add(subscription);
    }


    /**
     * 包装不是自己的协作医生
     */
    private void priorityNotReceptDataArrangement(DoctorDisplayBean bean) {
        DoctorRelationshipBean doctorRelationshipBean = new DoctorRelationshipBean();
        doctorRelationshipBean.setRelatedDoctor(bean);
        activity.dismissInteractionDialog();
        activity.getResult(doctorRelationshipBean,"Doctor", false);


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
                        activity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorPatientRelationshipBean> relationshipBean) {
                        for (DoctorPatientRelationshipBean bean : relationshipBean) {
                            boolean isActive = DateUtils.isActive(bean.isIsActive(), bean.getEndDate());
                            if (isActive) {
                                activity.getResult(bean, "Patient", false);
                            }
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        activity = null;
    }
}
