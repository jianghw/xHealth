package com.kaurihealth.mvplib.add_record_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/9/21.
 * <p/>
 * Describe:
 */
public class AddOutpatientPresenter<V> implements IAddOutpatientPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IAddOutpatientView mActivity;

    @Inject
    public AddOutpatientPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IAddOutpatientView) view;
    }

    @Override
    public void onSubscribe() {
        int mPatientId = mActivity.getPatientId();
        String mCategory = mActivity.getCategory();
        Subscription subscription = mRepository.loadAllPatientGenericRecordsBypatientIdAndCategory(mPatientId, mCategory)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    mActivity.loadingIndicator(true);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRecordDisplayDto>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showToast(e.getMessage());
                        mActivity.loadAllPatientGenericRecordsError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PatientRecordDisplayDto> bean) {
                        LogUtils.jsonDate(bean);
                        mActivity.loadAllPatientGenericRecordsSucceed(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.loadingIndicator(false);
        mSubscriptions.clear();
        mActivity = null;
    }
}
