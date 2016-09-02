package com.kaurihealth.mvplib.login_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.response_bean.TokenBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：登录逻辑
 */
public class LoginPresenter<V> implements ILoginPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ILoginView mActivity;

    @Inject
    LoginPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ILoginView) view;
    }

    /**
     * 事件订阅 登录
     */
    @Override
    public void onSubscribe() {
        LoginBean loginBean = mActivity.createRequestBean();
        Subscription subscription = mRepository.userLogin(loginBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();  //正在加载中...
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                 .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loginError(e);
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {
                        mActivity.showSuccessToast();
                        mActivity.dismissInteractionDialog();

                        if (tokenBean.getUser().getRegistPercentage() < 30) {
                            mActivity.completeRegister();
                        } else {
                            mActivity.loginSuccessful();
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

    /**
     * 历史登录信息
     */
    public void everLogin() {
        //        Subscription subscription = mRepository.getLoginDatabaseBean()
        //                .subscribeOn(Schedulers.io())
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .subscribe(
        //                        new Action1<TokenBean>() {
        //                            @Override
        //                            public void call(TokenBean tokenBean) {
        //
        //                            }
        //                        },
        //                        new Action1<Throwable>() {
        //                            @Override
        //                            public void call(Throwable throwable) {
        //
        //                            }
        //                        });
        //        mSubscriptions.add(subscription);
    }
}
