package com.kaurihealth.mvplib.resetpassword_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.ResetPasswordDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.constant.Global;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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

    //IMvpPresenter 设置指挥器,拥有mActivity
    @Override
    public void setPresenter(V view) {
        mActivity = (IResetPasswordView)view;
    }

    //IMvpPresenter 请求数据
    @Override
    public void onSubscribe() {
        ResetPasswordDisplayBean resetPasswordDisplayBean = mActivity.getResetPasswordDisplayBean();
        //请求短信验证码

        Subscription subscription = mRepository.resetPassword(resetPasswordDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
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
                        mActivity.showToast("重置密码失败"+e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        //请求成功, View提示正确信息, 这里的responsecode==200 封装好的
                        if(responseDisplayBean.isIsSucess()){
                            mActivity.showToast("重置密码成功！");
                            mActivity.switchPageUI(Global.Jump.LoginActivity);
                        }else{
                            mActivity.showToast(responseDisplayBean.getMessage());
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
