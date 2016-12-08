package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;

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
 * Created by mip on 2016/11/14.
 */

public class ComeMoneyFragmentPresenter<V> implements IComeMoneyFragmentPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IComeMoneyFragmentView mFragment;
    List<CreditTransactionDisplayBean> displayBeen = new ArrayList<>();

    @Inject
    ComeMoneyFragmentPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mFragment = (IComeMoneyFragmentView) view;
    }

    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.CreditTransactions()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mFragment.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<CreditTransactionDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mFragment.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(List<CreditTransactionDisplayBean> bean) {
                        for (CreditTransactionDisplayBean item : bean) {
                            switch (item.type) {
                                case "到款":
                                displayBeen.add(item);
                                    break;
                            }
                        }
                        mFragment.getBean(displayBeen);
                        displayBeen.clear();
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
