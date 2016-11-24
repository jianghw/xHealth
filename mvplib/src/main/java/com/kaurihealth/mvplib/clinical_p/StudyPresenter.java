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
 * Created by KauriHealth on 2016/8/26.
 */
public class StudyPresenter<V> implements IStudyPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IStudyView mFragment;
    private boolean mFirstLoad = true;
    private List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList;
    private ArrayList<Category> mListData;

    @Inject
    public StudyPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IStudyView) view;
    }

    @Override
    public void onSubscribe() {
//        if (mFirstLoad) loadingRemoteData(false);
        loadingRemoteData(mFirstLoad);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }

        Subscription subscription = mRepository.LoadAllMedicalLitreaturesByType("会议及学习信息")
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
                        mFragment.dataInteractionDialog();
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MedicalLiteratureDisPlayBean>() {
                    @Override
                    public void onCompleted() {
                        mFragment.dismissInteractionDialog();
                        mFragment.switchPageUI("跳转至DynamicActivity");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(MedicalLiteratureDisPlayBean medicalLiteratureDisPlayBean) {
                        mFragment.getMedicalLitreatures(medicalLiteratureDisPlayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void getArrayList() {
        ArrayList<Category> listDate = new ArrayList<>();
        Category categoryOne = new Category("会议及学习信息");

        mMedicalLiteratureDisPlayBeanList = mFragment.getMedicalLiteratureDisPlayBeanList();
        mListData = mFragment.getCategoryList();
        for (MedicalLiteratureDisPlayBean item : mMedicalLiteratureDisPlayBeanList) {
            String medicalLiteratureCategory = item.medicalLiteratureCategory;
            if (medicalLiteratureCategory.equals("会议及学习信息") && categoryOne.getItemBoolean()) {
                categoryOne.addItem(item);
            }
        }

        //判断Category对象里面是否有list 数量是否大于0
        if (categoryOne.getMedicalLiteratureDisPlayBeanItem().size() > 0) {
            listDate.add(categoryOne);
        }
        mListData.clear();
        mListData.addAll(listDate);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment.loadingIndicator(false);
        mFragment = null;
    }
}
