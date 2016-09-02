package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.NewPasswordDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/24.
 */
public class SetPasswordPresenter<V>  implements  ISetPasswordPresenter<V> {
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    //制造mActivity
    private  ISetPasswordView mActivity;

    //Dagger
    @Inject
    SetPasswordPresenter(Repository repository){
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
         mActivity = (ISetPasswordView)view;

    }

    //点击"提交"按钮网络请求数据
    @Override
    public void onSubscribe() {
        NewPasswordDisplayBean newPasswordDisplayBean = mActivity.getNewPasswordDisplayBean();
        //调用repository
        Subscription subscription = mRepository.updateUserPassword(newPasswordDisplayBean)
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
                        mActivity.showToast("修改密码失败!");
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                          mActivity.showToast("修改密码成功!");
                        //销毁当前页面,返回设置页面
                        mActivity.switchPageUI("SettingActivity.class");
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
