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
 * Created by mip on 2016/8/24.
 */
public class DynamicPresenter<V> implements IDynamicPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IDynamicView mFragment;
    private boolean mFirstLoad = true;
    private List<MedicalLiteratureDisPlayBean> mMedicalLiteratureDisPlayBeanList;
    private ArrayList<Category> mListData;


    @Inject
    public DynamicPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IDynamicView) view;
    }

    @Override
    public void onSubscribe() {
//        if (mFirstLoad) {
//            loadingRemoteData(false);
//        }
        loadingRemoteData(mFirstLoad);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }

        Subscription subscription = mRepository.LoadAllMedicalLitreaturesByType("医疗动态")
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


    @Override
    public void LoadMedicalLiteratureById(int medicalLiteratureId) {
        Subscription subscription = mRepository.LoadMedicalLiteratureById(medicalLiteratureId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mFragment.dataInteractionDialog();
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

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment.loadingIndicator(false);
        mFragment = null;
    }

    /**
     * 为category分发数据
     */
    public void getArrayList() {
        ArrayList<Category> listDate = new ArrayList<>();
        Category categoryThree = new Category("行业动态");
        Category categoryTwo = new Category("医学信息");
        Category categoryOne = new Category("院室介绍");

        mMedicalLiteratureDisPlayBeanList = mFragment.getMedicalLiteratureDisPlayBeanList();
        mListData = mFragment.getCategoryList();
        for (MedicalLiteratureDisPlayBean item : mMedicalLiteratureDisPlayBeanList) {
            String medicalLiteratureCategory = item.medicalLiteratureCategory;

            if (medicalLiteratureCategory.equals("行业动态") && categoryThree.getItemBoolean()) {
                categoryThree.addItem(item);
            }
            if (medicalLiteratureCategory.equals("医学信息") && categoryTwo.getItemBoolean()) {
                categoryTwo.addItem(item);
            }
            if (medicalLiteratureCategory.equals("院室介绍") && categoryOne.getItemBoolean()) {
                categoryOne.addItem(item);
            }
        }

        //判断Category对象里面是否有list 数量是否大于0
        if (categoryOne.getMedicalLiteratureDisPlayBeanItem().size() > 0) {
            listDate.add(categoryOne);
        }
        if (categoryTwo.getMedicalLiteratureDisPlayBeanItem().size() > 0) {
            listDate.add(categoryTwo);
        }
        if (categoryThree.getMedicalLiteratureDisPlayBeanItem().size() > 0) {
            listDate.add(categoryThree);
        }
        mListData.clear();
        mListData.addAll(listDate);

    }
}
