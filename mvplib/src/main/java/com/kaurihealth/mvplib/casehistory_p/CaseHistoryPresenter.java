package com.kaurihealth.mvplib.casehistory_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2017/1/9.
 */

public class CaseHistoryPresenter<V> implements ICaseHistoryPresenter<V>{
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private ICaseHistoryView mFragment;
    private boolean mFirstLoad = true;


    @Inject
    CaseHistoryPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (ICaseHistoryView) view;
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
        clearCaseHistoryData();
    }

    /**
     * 清除已处理病历
     */
    @Override
    public void clearCaseHistoryData() {

    }

    @Override
    public void unSubscribe() {
        if (mFragment != null)
//            mFragment.loadingIndicator(false);
        mSubscriptions.clear();
        mFragment = null;
    }
}
