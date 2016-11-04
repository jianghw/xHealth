package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by KauriHealth on 2016/9/18.
 */
public class AccountDetailsPresenter<V> implements IAccountDetailsPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IAccountDetailsView mActivity;

    @Inject
    public AccountDetailsPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IAccountDetailsView) view;
    }

    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.CreditTransactions()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CreditTransactionDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(List<CreditTransactionDisplayBean> been) {
                        mActivity.getAmountDetail(been);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
