package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.home_p.IReferralPatientRequestPresenter;
import com.kaurihealth.utilslib.log.LogUtils;

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
 * Created by KauriHealth on 2016/10/24.
 */

public class ReferrcalDoctorPresenter<V> implements IReferralPatientRequestPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IReferralDoctorView mActivity;

    @Inject
    public ReferrcalDoctorPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IReferralDoctorView) view;
    }


    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.loadContactListByDoctorId()
                .subscribeOn(Schedulers.io())
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
                                LogUtils.e(throwable.getMessage());                            }
                        });
        mSubscriptions.add(subscription);
    }

    private void updateDataByDirty() {
        Subscription subscription = mRepository.loadAllDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorRelationshipBean> beanList) {
                        priorityDataArrangement(beanList);
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
    public void priorityDataArrangement(List<DoctorRelationshipBean> list) {
        if (list == null || list.isEmpty()) return;
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
                        new Action1<List<DoctorRelationshipBean>>() {
                            @Override
                            public void call(List<DoctorRelationshipBean> doctorRelationshipBeen) {
                                mActivity.getDoctorRelationshipBeanList(doctorRelationshipBeen);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
