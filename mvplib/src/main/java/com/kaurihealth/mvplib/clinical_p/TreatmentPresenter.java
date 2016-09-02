package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureDisPlayBean;
import com.kaurihealth.datalib.request_bean.builder.Category;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/8/26.
 */
public class TreatmentPresenter<V> implements ITreatmentPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private ITreatmentView mFragment;
    private boolean mFirstLoad = true;
    private List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList;
    private ArrayList<Category> mListData;

    @Inject
    public TreatmentPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mFragment = (ITreatmentView) view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }

        Subscription subscription = mRepository.LoadAllMedicalLitreaturesByType("疾病与治疗")
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mFragment.loadingIndicator(true);
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<MedicalLiteratureDisPlayBean>>() {
                    @Override
                    public void onCompleted() {
                        mFragment.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.loadingIndicator(false);
                        mFragment.getAllMedicalLitreaturesError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<MedicalLiteratureDisPlayBean> medicalLiteratureDisPlayBeen) {
                        mFragment.getAllMedicalLitreaturesSuccess(medicalLiteratureDisPlayBeen);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void LoadMedicalLiteratureById(int medicalLiteratureId) {
        Subscription subscription = mRepository.LoadMedicalLiteratureById(medicalLiteratureId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mFragment.loadingIndicator(true);
                        mFirstLoad = false;
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
                        mFragment.getMedicalLitreatures(medicalLiteratureDisPlayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment.loadingIndicator(false);
        mFragment = null;
    }
}
