package com.kaurihealth.mvplib.open_an_account_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.NewRegistByDoctorBean;
import com.kaurihealth.datalib.response_bean.RegisterResponse;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/22.
 */
public class OpenAnAccountPresenter<T> implements IOpenAnAccountPresenter<T> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    //造mActivity
    private IOpenAnAccountView mActivity;


    //Dagger
    @Inject
    OpenAnAccountPresenter(Repository repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }


    //IMvpPresenter 设置presenter
    @Override
    public void setPresenter(T view) {
        //初始化mActivity
        mActivity = (IOpenAnAccountView) view;
    }

    //订阅  IMvpPresenter  请求数据
    @Override
    public void onSubscribe() {
        //创造bean
        NewRegistByDoctorBean newRegistByDoctorBean = mActivity.getNewRegistByDoctorBean();
        Subscription subscription = mRepository.openAnAccount(newRegistByDoctorBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();    //sweetDialog显示"正在加载中....."
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterResponse registerResponse) {
                        if (registerResponse.isSuccessful()) {
                            mActivity.openAnAccountSuccess(registerResponse);
                        } else {
                            mActivity.displayErrorDialog(registerResponse.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscription);

    }

    //取消订阅
    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.dismissInteractionDialog();
        mSubscriptions.clear();
        mActivity = null;
    }
}
