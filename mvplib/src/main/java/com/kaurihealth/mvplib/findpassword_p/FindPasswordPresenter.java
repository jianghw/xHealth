package com.kaurihealth.mvplib.findpassword_p;

import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.RequestResetPasswordDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.InitiateVerificationBeanBuilder;
import com.kaurihealth.datalib.request_bean.builder.RequestResetUserPasswordBuilder;
import com.kaurihealth.datalib.response_bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.RegularUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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

    //发送验证码
    @Override
    public void sendVerificationCodeRequest() {
        String phoneNumber = mActivity.getPhoneNumber();
        if (RegularUtils.matcherMyContent("^1[3|4|5|7|8]\\d{9}$", phoneNumber)) {
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
     * @param verificationBean
     */
    private void getVerificationCode(InitiateVerificationBean verificationBean) {
        Subscription subscribe = mRepository.InitiateVerification(verificationBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InitiateVerificationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showToast("短信验证码发送失败" + e.getMessage());
                    }

                    @Override
                    public void onNext(InitiateVerificationResponse response) {
                        if (response.isSuccessful()) {
                            mActivity.showToast("短信验证码发送成功");
                        } else {
                            mActivity.showToast(response.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscribe);
    }

    /**
     * 事件订阅:点击了界面“下一步“按钮进行验证
     */
    @Override
    public void onSubscribe() {
        //手机号码,造bean
        String phoneNumber = mActivity.getPhoneNumber();
        //验证码, 造bean
        String versionCode = mActivity.getVersionCodeContent();
        RequestResetPasswordDisplayBean newRequestResetUserPasswordBean = new RequestResetUserPasswordBuilder().Build(phoneNumber, versionCode);

        //请求短信验证码
        Subscription subscription = mRepository.findPassword(newRequestResetUserPasswordBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {//请求失败,也就是请求码 !=200 任何情况下都是走onError的方法
                        mActivity.displayErrorDialog("验证失败" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {   //请求完成数据,在对返回的数据进行处理
                        if (responseDisplayBean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
                            mActivity.switchToActivity(responseDisplayBean);
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
}
