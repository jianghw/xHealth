package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;
import com.kaurihealth.utilslib.constant.Global;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/19.
 * <p/>
 * Describe:
 */
public class SearchCommonFragmentPresenter<V> implements ISearchCommonFragmentPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ISearchCommonFragmentView mFragment;

    @Inject
    public SearchCommonFragmentPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (ISearchCommonFragmentView) view;
    }

    @Override
    public void onSubscribe() {
        String title = mFragment.getTitle();
        List<SearchBooleanResultBean> list = mFragment.getSearchList();
        if (list == null || list != null && list.isEmpty()) {
            mFragment.processingData(list);
        } else {
            processingData(list, title);
        }
    }

    @Override
    public void processingData(List<SearchBooleanResultBean> list, String title) {
        String content = mFragment.getEditTextContent();
        List<SearchBooleanResultBean> newList = new ArrayList<>();
        newList.addAll(list);
        Subscription subscription = Observable.from(newList)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<SearchBooleanResultBean, Boolean>() {
                    @Override
                    public Boolean call(SearchBooleanResultBean bean) {
                        SearchResultBean.ResultBean.ItemsBean itemsBean = bean.getItemsBean();
                        if (itemsBean == null) return true;
                        switch (title) {
                            case Global.Bundle.SEARCH_DEFAULT:
                                return true;
                            case Global.Bundle.SEARCH_HOSPITAL:
                                return itemsBean.getHospitalname() != null ? itemsBean.getHospitalname().contains(content)
                                        : itemsBean.getHospitaltitle() != null ? itemsBean.getHospitaltitle().contains(content) : false;
                            case Global.Bundle.SEARCH_DEPARTMENT:
                                return itemsBean.getDepartmentname() != null ? itemsBean.getDepartmentname().contains(content) : false;
                            case Global.Bundle.SEARCH_DOCTOR:
                                return itemsBean.getFullname() != null ? itemsBean.getFullname().contains(content) : false;
                            default:
                                return true;
                        }
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<SearchBooleanResultBean>>() {
                            @Override
                            public void call(List<SearchBooleanResultBean> been) {
                                mFragment.processingData(been);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mFragment.showToast(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment = null;
    }
}
