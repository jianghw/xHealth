package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.request_bean.bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 24/08/2016.
 */
public class WithdrawalPresenter<V> implements IWithdrawalPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IWithdrawalView mActivity;


    @Inject
    WithdrawalPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IWithdrawalView) view;
    }

    @Override
    public void onSubscribe() {
        final Observable<List<UserCashOutAccountDisplayBean>> listcall = mRepository.getUserCashOutAccounts();
        Subscription subscription = listcall.subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())  //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserCashOutAccountDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mActivity.showToast("获取提现账号信息失败！");
                    }

                    @Override
                    public void onNext(List<UserCashOutAccountDisplayBean> userCashOutAccountDisplayBeen) {
                        mActivity.showToast("获取提现账号信息成功！");
                        try {
                            //强制向下转型
                            mActivity.createAccountDialog((List<UserCashOutAccountDisplayBean>) listcall);
                        } catch (Exception e) {
                            e.printStackTrace();
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


    //发送提现请求
    @Override
    public  void  startNewCashOut(){
        NewCashOutBean newCashOutBean = mActivity.getNewCashOutBean();
        //发起提现请求
        Subscription subscription = mRepository.startNewCashOut(newCashOutBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())  //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showToast("提现请求发送失败！");
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        mActivity.showToast("您的提现请求已发送完成!");
                        //关闭当前页面
                        mActivity.getHandlerInstance();
                    }
                });
        mSubscriptions.add(subscription);
    }
}
