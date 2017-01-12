package com.kaurihealth.mvplib.doctor_new;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
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
 * Created by mip on 2016/12/13.
 * <p/>
 * Describe:
 */

public class DoctorPresenter<V> implements IDoctorPresenter<V> {
    private final Repository mRepository;
    private IDoctorView mFragment;
    private CompositeSubscription mSubscriptions;
    private boolean mFirstLoad = true;

    @Inject
    public DoctorPresenter(Repository mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IDoctorView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(false);
    }

    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();  //手动刷新
        }
        loadDoctorPatientRelationshipForDoctor();
    }

    /**
     * 查询所有用户医生关系的病例（包含拒绝的） 医生使用的token
     */
    private void loadDoctorPatientRelationshipForDoctor() {
        Subscription subscription = mRepository.loadAllDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mFragment.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
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
                        //将同意和自己成为协作医生的doctor过滤出来
                        priorityDataArrangement(beanList);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 将同意和自己成为协作医生的doctor过滤出来
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

    @Override
    public void unSubscribe() {
        if (mFragment != null) mFragment.loadingIndicator(false);
        mSubscriptions.clear();
        mFragment = null;
    }

    /**
     * 查询挂起的医生关系 小红点
     */
    public void loadPendingDoctorRelationships(boolean isDirty) {
        Subscription subscription = mRepository.LoadPendingDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorRelationshipBean> doctorRelationshipBean) {
                        mFragment.isHasDoctorRequest(false);
                        for (DoctorRelationshipBean bean : doctorRelationshipBean) {
                            if (bean != null && bean.getStatus().equals("等待") && !isMySend(bean)) {
                                mFragment.isHasDoctorRequest(true);
                                break;
                            }
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    private boolean isMySend(DoctorRelationshipBean bean) {
        int sourceDoctorId = bean.getSourceDoctorId();
        DoctorDisplayBean displayBean = LocalData.getLocalData().getMyself();
        if (displayBean == null) return false;
        int doctorId = displayBean.getDoctorId();
        return sourceDoctorId == doctorId;
    }
}
