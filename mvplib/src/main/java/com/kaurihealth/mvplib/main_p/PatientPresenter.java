package com.kaurihealth.mvplib.main_p;

import android.annotation.TargetApi;
import android.os.Build;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 * 描述：登录逻辑
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

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }
        Subscription subscription = mRepository.loadDoctorPatientRelationshipForDoctor()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //加载中...
                        mFragment.loadingIndicator(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
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
                        mFragment.lazyLoadingDataError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorPatientRelationshipBean> list) {
                        mFragment.lazyLoadingDataSuccess(list);
//                        LogUtils.json(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create().toJson(list));
                    }
                });
        mSubscriptions.add(subscription);
    }


    @TargetApi(Build.VERSION_CODES.N)
    public void lazyLoadingDataSuccess(List<DoctorPatientRelationshipBean> list) {
        list.stream().map((cost) -> cost.getPatientId()).forEach(System.out::println);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment.loadingIndicator(false);
        mFragment = null;
    }
}
