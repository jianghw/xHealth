package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.PriceDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.constant.Global;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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
        NewPriceBean newPriceBean = mActivity.createNewPriceBean();
        //调用repository
        Subscription subscription = mRepository.insertPrice(newPriceBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
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

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    @Override
    public void updatePrice() {
        PriceDisplayBean priceDisplayBean = mActivity.getPriceDisplayBean();
        if (priceDisplayBean == null) {
            mActivity.showToast("数据出错,请重新登录");
            return;
        }
        //调用repository
        Subscription subscription = mRepository.updateDoctorProductPrice(priceDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
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
                        //销毁当前页面,返回设置页面
                        if (responseDisplayBean.isIsSucess()) {
                            loadDoctorDetail();
                        } else {
                            mActivity.displayErrorDialog(responseDisplayBean.getMessage());
                        }
                    }
                });

        mSubscriptions.add(subscription);
    }

    /**
     * 查询医生信息
     */
    public void loadDoctorDetail() {
        Subscription subscription = mRepository.loadDoctorDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                        mActivity.showToast("保存成功!");
                        mActivity.switchPageUI(Global.Jump.ServiceSettingActivity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean doctorDisplayBean) {
                        LocalData.getLocalData().setMyself(doctorDisplayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
