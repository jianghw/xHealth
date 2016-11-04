package com.kaurihealth.mvplib.patient_p;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by KauriHealth on 2016/10/26.
 */

public class ReferralPatientPresenter<V> implements IReferralPatientPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IReferralPatientView mActivity;

    @Inject
    public ReferralPatientPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IReferralPatientView) view;
    }

    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.loadContactListByDoctorId()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<ContactUserDisplayBean>>() {
                            @Override
                            public void call(List<ContactUserDisplayBean> beanList) {
                                updateDataByDirty();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mActivity.dismissInteractionDialog();
                            }
                        });
        mSubscriptions.add(subscription);
    }

    private void updateDataByDirty() {
        Subscription subscription = mRepository.loadDoctorPatientRelationshipForDoctor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showToast(e.getMessage());
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(List<DoctorPatientRelationshipBean> list) {
                        priorityDataArrangement(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 过滤历史数据，按时间排序
     * 1、排序 降序
     * 2、observable<> 重发
     * 3、 过滤
     * 4、 排序 降序
     *
     * @param list
     */
    public void priorityDataArrangement(List<DoctorPatientRelationshipBean> list) {
        ArrayMap<String, DoctorPatientRelationshipBean> arrayMap = new ArrayMap<>();
        if (list == null) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        String key = getStringKeyToMap(doctorPatientRelationshipBean);
                        return !arrayMap.containsKey(key);
                    }
                })
                .map(new Func1<DoctorPatientRelationshipBean, DoctorPatientRelationshipBean>() {
                    @Override
                    public DoctorPatientRelationshipBean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        String key = getStringKeyToMap(doctorPatientRelationshipBean);
                        arrayMap.put(key, doctorPatientRelationshipBean);
                        return doctorPatientRelationshipBean;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                        if (!arrayMap.isEmpty()) arrayMap.clear();

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(List<DoctorPatientRelationshipBean> list) {
                        listFilteringActiveProcessing(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 过滤出活动的
     *
     * @param list
     */
    private void listFilteringActiveProcessing(List<DoctorPatientRelationshipBean> list) {
        if (list == null) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        return DateUtils.isActive(doctorPatientRelationshipBean.isIsActive(), doctorPatientRelationshipBean.getEndDate());
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<DoctorPatientRelationshipBean>>() {
                            @Override
                            public void call(List<DoctorPatientRelationshipBean> doctorPatientRelationshipBeen) {
                                mActivity.getResultBean(doctorPatientRelationshipBeen);
                                mActivity.dismissInteractionDialog();
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e(throwable.getMessage());
                                mActivity.dismissInteractionDialog();
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @NonNull
    private String getStringKeyToMap(DoctorPatientRelationshipBean bean) {
        int mPatientID = bean.getPatientId();
        String mShipReason = bean.getRelationshipReason();
        return mPatientID + mShipReason;
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
