package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.utilslib.constant.Global;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MedicalRecordPresenter<V> implements IMedicalRecordPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IMedicalRecordView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    MedicalRecordPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IMedicalRecordView) view;
    }

    /**
     * 通过患者ID查询所有的患者的病例 医生和患者的token都可以用
     */
    @Override
    public void onSubscribe() {
        loadingRemoteData(true);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }
        Subscription subscription = mRepository.loadAllPatientGenericRecordsBypatientId(mActivity.getPatientId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRecordDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                        mFirstLoad = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PatientRecordDisplayBean> list) {
                        groupDataByList(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void groupDataByList(List<PatientRecordDisplayBean> list) {
        listGroupFilter(list, Global.Environment.CLINICAL);
        listGroupFilter(list, Global.Environment.LOB);
        listGroupFilter(list, Global.Environment.AUXILIARY);
        listGroupFilter(list, Global.Environment.PATHOLOGY);
    }

    /**
     * 数据过滤
     */
    @Override
    public void listGroupFilter(List<PatientRecordDisplayBean> list, String title) {
        if (list == null) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(bean -> bean.getCategory().equals(title))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(beanList -> {
                    dataCallBack(beanList, title);
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void dataCallBack(List<PatientRecordDisplayBean> beanList, String title) {
        switch (title) {
            case Global.Environment.CLINICAL:
                mActivity.callBackClinical(beanList);
                break;
            case Global.Environment.LOB:
                mActivity.callBackLob(beanList);
                break;
            case Global.Environment.AUXILIARY:
                mActivity.callBackAuxiliary(beanList);
                break;
            case Global.Environment.PATHOLOGY:
                mActivity.callBackPathology(beanList);
                break;
            default:
                break;
        }
    }

    /**
     * 弹出新添加
     */
    @Override
    public void currentlySelectedByDialog(String category, String subject) {
        Observable.just(subject)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String activityName) {
                        mActivity.sendEventBusByString(category, activityName);
                    }
                });
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity.loadingIndicator(false);
        mActivity = null;
    }
}
