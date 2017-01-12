package com.kaurihealth.mvplib.casehistory_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;

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
public class AllMedicalRecordsPresenter<V> implements IAllMedicalRecordsPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IAllMedicalRecordsView mFragment;
    private boolean mFirstLoad = true;

    @Inject
    AllMedicalRecordsPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IAllMedicalRecordsView) view;
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
        loadDoctorMainPage();
    }

    /**
     * 医生首页api
     */
    @Override
    public void loadDoctorMainPage() {
        Subscription subscription = mRepository.loadDoctorMainPage()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mFragment.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<List<MainPagePatientDisplayBean>>() {
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
                            public void onNext(List<MainPagePatientDisplayBean> list) {
                                mFragment.loadDoctorMainPageSucceed(list);
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 手动搜索
     */
    @Override
    public void manualSearch(CharSequence text, List<MainPagePatientDisplayBean> list) {
        Subscription subscription = Observable.from(list)
                .filter(new Func1<MainPagePatientDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(MainPagePatientDisplayBean bean) {
                        return null != bean;
                    }
                })
                .filter(new Func1<MainPagePatientDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(MainPagePatientDisplayBean bean) {
                        return bean.getPatientFullName().contains(text);
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MainPagePatientDisplayBean>>() {
                    @Override
                    public void call(List<MainPagePatientDisplayBean> beanList) {
                        mFragment.manualSearchSucceed(beanList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mFragment.showToast(throwable.getMessage());
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
