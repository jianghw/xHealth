package com.kaurihealth.mvplib.main_p;

import android.support.annotation.NonNull;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
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
 * Created by jianghw on 2016/8/5.
 * <p>
 */
public class PatientPresenter<V> implements IPatientPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IPatientView mFragment;
    private boolean mFirstLoad = true;

    @Inject
    PatientPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IPatientView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(false);
    }

    /**
     * 为现医生查询所有医患关系
     */
    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }
        loadContactListByDoctorId();
    }

    /**
     * 关系列表
     */
    @Override
    public void loadContactListByDoctorId() {
        Subscription subscription = mRepository.loadContactListByDoctorId()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mFragment.loadingIndicator(true))
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
                                mFragment.loadingIndicator(false);
                                mFragment.showToast(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 当前医生的患者
     */
    private void updateDataByDirty() {
        Subscription subscription = mRepository.loadDoctorPatientRelationshipForDoctor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                        mFragment.loadingIndicator(false);
                        mFirstLoad = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.loadingIndicator(false);
                        mFragment.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorPatientRelationshipBean> list) {
                        priorityDataArrangement(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 过滤历史数据，如有排在前面的 后面不要
     * 1、 过滤 不包含的数据
     * 4、 排序 降序
     */
    public void priorityDataArrangement(List<DoctorPatientRelationshipBean> list) {
        List<String> arrayList = new ArrayList<>();
        if (list == null) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        String key = getStringKeyToMap(doctorPatientRelationshipBean);
                        return !arrayList.contains(key);
                    }
                })
                .map(new Func1<DoctorPatientRelationshipBean, DoctorPatientRelationshipBean>() {
                    @Override
                    public DoctorPatientRelationshipBean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        String key = getStringKeyToMap(doctorPatientRelationshipBean);
                        arrayList.add(key);
                        return doctorPatientRelationshipBean;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                        if (!arrayList.isEmpty()) arrayList.clear();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.showToast(e.getMessage());
                        LogUtils.e(e.getMessage());
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
                                noActiveProcessing(doctorPatientRelationshipBeen, list);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mFragment.showToast(throwable.getMessage());
                                LogUtils.e(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 过滤出不活动的
     * 1 过滤出没激活的
     * 2 转list
     * 3 重新发
     * 4 过滤出重复的
     * 5 转list
     */
    private void noActiveProcessing(List<DoctorPatientRelationshipBean> isActiveBean, List<DoctorPatientRelationshipBean> list) {
        List<Integer> arrayList = new ArrayList<>();
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean patientRelationshipBean) {
                        return !DateUtils.isActive(patientRelationshipBean.isIsActive(), patientRelationshipBean.getEndDate());
                    }
                })
                .toList()
                .flatMap(new Func1<List<DoctorPatientRelationshipBean>, Observable<DoctorPatientRelationshipBean>>() {
                    @Override
                    public Observable<DoctorPatientRelationshipBean> call(List<DoctorPatientRelationshipBean> doctorPatientRelationshipBeen) {
                        return Observable.from(doctorPatientRelationshipBeen);
                    }
                })
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        return !arrayList.contains(doctorPatientRelationshipBean.getPatientId());
                    }
                })
                .map(new Func1<DoctorPatientRelationshipBean, DoctorPatientRelationshipBean>() {
                    @Override
                    public DoctorPatientRelationshipBean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        arrayList.add(doctorPatientRelationshipBean.getPatientId());
                        return doctorPatientRelationshipBean;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<DoctorPatientRelationshipBean>>() {
                            @Override
                            public void call(List<DoctorPatientRelationshipBean> notActive) {
                                isActiveBean.addAll(isActiveBean.size(), notActive);
                                mFragment.lazyLoadingDataSuccess(isActiveBean);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mFragment.showToast(throwable.getMessage());
                                LogUtils.e(throwable.getMessage());
                            }
                        },
                        new Action0() {
                            @Override
                            public void call() {
                                if (!arrayList.isEmpty()) arrayList.clear();
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
        mFragment.loadingIndicator(false);
        mFragment = null;
    }
}
