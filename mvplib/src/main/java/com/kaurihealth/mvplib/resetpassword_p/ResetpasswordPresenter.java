package com.kaurihealth.mvplib.resetpassword_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/18.
 * 描述: 指挥 重置密码逻辑
 */
public class ResetpasswordPresenter<V> implements IResetpasswordPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    //造mActivity
    private IResetPasswordView mActivity;

    //Dagger
    @Inject
    ResetpasswordPresenter(Repository repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();

    }

    //点击右上角“完成”按钮请求数据
    @Override
    public void clickFinishButton() {
        ResetPasswordDisplayBean resetPasswordDisplayBean = mActivity.getResetPasswordDisplayBean();
        //请求短信验证码

        Subscription subscription = mRepository.resetPassword(resetPasswordDisplayBean)
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
                        //请求失败,也就是请求码 !=200 任何情况下都是走onError的方法
                        mActivity.showToast("重置密码失败！");
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        //请求成功, View提示正确信息, 这里的responsecode==200 封装好的
                        mActivity.showToast("重置密码成功！");
                        mActivity.switchPageUI("LoginActivity.class");
                    }
                });
        mSubscriptions.add(subscription);

    }

    //IMvpPresenter 设置指挥器,拥有mActivity
    @Override
    public void setPresenter(V view) {
        mActivity = (IResetPasswordView)view;

    }

    //IMvpPresenter 请求数据
    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;

    }
}
