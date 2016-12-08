package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.mvplib.main_p.IClinicalPresenter;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/1.
 */
public class ClinicalSearchPresenter<V> implements IClinicalPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IClinicalSearchView mActivity;

    @Inject
    public ClinicalSearchPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();

    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IClinicalSearchView) view;
    }

    @Override
    public void onSubscribe() {

        int medicalLiteratureId = mActivity.getCurrentMedicalLiteratureId();
        Subscription subscription = mRepository.LoadMedicalLiteratureById(medicalLiteratureId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MedicalLiteratureDisPlayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean) {
                        mActivity.getMedicalLiteratureDisPlayBean(medicalLiteratureDisPlayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void getliteratureDisPlayBeanList(){

        Subscription subscription = mRepository.LoadAllMedicalLiteratures()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MedicalLiteratureDisPlayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeen) {

                        mActivity.getliteratureDisPlayBeanList(medicalLiteratureDisPlayBeen);
                    }
                });
        mSubscriptions.add(subscription);
    }
    @Override
    public void unSubscribe() {
    mSubscriptions.clear();
    }
}
