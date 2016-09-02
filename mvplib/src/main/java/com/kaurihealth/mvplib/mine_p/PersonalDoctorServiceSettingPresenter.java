package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.request_bean.bean.PriceDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.constant.Global;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 24/08/2016.
 */
public class PersonalDoctorServiceSettingPresenter<V> implements IPersonalDoctorServiceSettingPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IPersonalDoctorServiceSettingView mActivity;

    @Inject
    PersonalDoctorServiceSettingPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IPersonalDoctorServiceSettingView) view;
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    @Override
    public void updatePrice() {
        PriceDisplayBean priceDisplayBean = mActivity.getPriceDisplayBean();
        //调用repository
        Subscription subscription = mRepository.updateDoctorProductPrice(priceDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showToast("修改价格失败!");
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        mActivity.showToast("修改价格成功!");
                        //销毁当前页面,返回设置页面
                        mActivity.switchPageUI(Global.Jump.ServiceSettingActivity);
                    }
                });

        mSubscriptions.add(subscription);
    }

    @Override
    public void insertPrice() {
        NewPriceBean newPriceBean = mActivity.createNewPriceBean();
        //调用repository
        Subscription subscription = mRepository.insertPrice(newPriceBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showToast("修改价格失败!");
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        mActivity.showToast("修改价格成功!");
                        //销毁当前页面,返回设置页面
                        mActivity.switchPageUI(Global.Jump.ServiceSettingActivity);
                    }
                });

        mSubscriptions.add(subscription);
    }
}
