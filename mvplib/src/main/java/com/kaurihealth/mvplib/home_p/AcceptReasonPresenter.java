package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDecisionBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/21.
 */
public class AcceptReasonPresenter<V> implements IAcceptReasonPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IAcceptReasonView mActivity;

    @Inject
    public AcceptReasonPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IAcceptReasonView) view;
    }

    @Override
    public void onSubscribe() {
        PatientRequestDecisionBean bean = mActivity.getCurrentPatientRequestDecisionBean();
        Subscription subscription = mRepository.AcceptPatientRequest(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    mActivity.dataInteractionDialog();  //正在加载中...
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.showToast("患者预约已同意");
                            mActivity.getRequestResult(bean);
                        } else {
                            mActivity.displayErrorDialog(bean.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
