package com.kaurihealth.mvplib.findpassword_p;

import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayDto;
import com.kaurihealth.datalib.request_bean.builder.InitiateVerificationBeanBuilder;
import com.kaurihealth.datalib.request_bean.builder.RequestResetUserPasswordBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/15.
 * <p/>
 * 描述: 忘记密码逻辑
 */
public class FindPasswordPresenter<V> implements IFindPasswordPresenter<V> {
    private final Repository mRepository;
    private IFindPasswordView mActivity;
    private CompositeSubscription mSubscriptions;


    @Inject
    FindPasswordPresenter(Repository repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IFindPasswordView) view;
    }

    /*
         事件订阅:点击了界面“下一步“按钮进行验证
        */
    @Override
    public void onSubscribe() {
        //手机号码,造bean
        String phoneNumber = mActivity.getPhoneNumber();
        //验证码, 造bean
        String versionCode = mActivity.getVersionCodeContent();
        RequestResetPasswordDisplayDto newRequestResetUserPasswordBean = new RequestResetUserPasswordBuilder().Build(phoneNumber, versionCode);

        //请求短信验证码
        Subscription subscription = mRepository.findPassword(newRequestResetUserPasswordBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();    //sweetDialog显示"正在加载中....."
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
                        mActivity.showVerificationCodeError(e);
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {   //请求完成数据,在对返回的数据进行处理
                        mActivity.switchToActivity(responseDisplayBean);
                    }
                });

        mSubscriptions.add(subscription);


    }

    @Override
    public void unSubscribe() {

    }

    //发送验证码
    @Override
    public void sendVerificationCodeRequest() {
        String phoneNumber = mActivity.getPhoneNumber();
        if (phoneNumber.matches("^1[3|4|5|7|8]\\d{9}$")) {
            InitiateVerificationBean initiateVerificationBean = new InitiateVerificationBeanBuilder().BuildResetPasswordInitiateVerification(phoneNumber);
            getVerificationCode(initiateVerificationBean); //要求短信中心发送验证码，远程数据remoteData处理
            mActivity.startCountDown();
        } else {
            //打印出来是：请输入正确的电话号码
            mActivity.showPhoneNumberErrorMessage();
        }

    }


    /**
     * 请求短信中心，要求获得验证码
     *
     * @param initiateVerificationBean
     */
    private void getVerificationCode(InitiateVerificationBean initiateVerificationBean) {
        Subscription subscribe = mRepository.InitiateVerification(initiateVerificationBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InitiateVerificationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.showToast("短信验证码发送失败");
                    }

                    @Override
                    public void onNext(InitiateVerificationResponse initiateVerificationResponse) {
                        LogUtils.i("Success", initiateVerificationResponse.message);
                        mActivity.showToast("短信验证码发送成功");
                    }
                });
        mSubscriptions.add(subscribe);

    }
}
