package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewCashOutBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.UserCashOutAccountDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 24/08/2016.
 */
public class WithdrawalPresenter<V> implements IWithdrawalPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IWithdrawalView mActivity;
    private boolean mFirstLoad = true;

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
        if (mFirstLoad) loadUserCashOutAccounts(false);
    }

    @Override
    public void loadUserCashOutAccounts(boolean isDirty) {
        Subscription subscription = mRepository.getUserCashOutAccounts()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())  //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserCashOutAccountDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mFirstLoad = false;
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<UserCashOutAccountDisplayBean> userCashOutAccountDisplayBeen) {
                        createArrayStringsByList(userCashOutAccountDisplayBeen);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 1、拼接 银行名+卡号
     * 2、转list
     * 3、末尾添加 新卡
     * 4、转数组
     */
    private void createArrayStringsByList(List<UserCashOutAccountDisplayBean> beanList) {
        if (beanList == null || beanList.isEmpty()) {
            String[] strings = new String[]{"使用新卡提现"};
            mActivity.createAccountDialog(beanList, strings);
            return;
        }
        Subscription subscription = Observable.from(beanList)
                .map(userCashOutAccountDisplayBean -> {
                    String accountType = userCashOutAccountDisplayBean.getAccountType();
                    String detail = userCashOutAccountDisplayBean.getAccountDetail();
                    return String.format("%s ( %s )", accountType,
                            detail.substring(detail.length() > 4 ? detail.length() - 4 : 0, detail.length()));
                })
                .toList()
                .map(strings -> {
                    strings.add(strings.size(), "使用新卡提现");
                    return strings;
                })
                .map(stringList -> stringList.toArray(new String[stringList.size()]))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        strings -> {
                            mActivity.createAccountDialog(beanList, strings);
                        }, throwable -> {
                            LogUtils.e(throwable.getMessage());
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
    public void startNewCashOut() {
        NewCashOutBean newCashOutBean = mActivity.getNewCashOutBean();
        //发起提现请求
        Subscription subscription = mRepository.startNewCashOut(newCashOutBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())  //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        if (responseDisplayBean.isIsSucess()) {
                            loadDoctorDetail();
                        } else {
                            mActivity.displayErrorDialog(responseDisplayBean.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void loadDoctorDetail() {
        Subscription subscription = mRepository.loadDoctorDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean doctorDisplayBean) {
                        LocalData.getLocalData().setMyself(doctorDisplayBean);
                        mActivity.startNewCashSucceed();
                    }
                });
        mSubscriptions.add(subscription);
    }
}
