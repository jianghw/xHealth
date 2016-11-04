package com.kaurihealth.mvplib.main_p;

import android.support.annotation.NonNull;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
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
 * Created by jianghw on 2016/8/5.
 * <p>
 */
public class DoctorPresenter<V> implements IPatientPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IPatientView mFragment;
    private boolean mFirstLoad = true;

    @Inject
    DoctorPresenter(IDataSource repository) {
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

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();  //手动刷新
        }
        loadContactListByDoctorId();
    }

    private void updateDataByDirty() {
        Subscription subscription = mRepository.loadAllDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorRelationshipBean>>() {
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
    private void priorityDataArrangement(List<DoctorRelationshipBean> list) {
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
                                mFragment.lazyLoadingDataSuccess(doctorRelationshipBeen);
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
