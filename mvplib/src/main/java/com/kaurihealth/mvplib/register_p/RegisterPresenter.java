package com.kaurihealth.mvplib.register_p;

import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.request_bean.bean.NewRegisterBean;
import com.kaurihealth.datalib.request_bean.bean.RegisterResponse;
import com.kaurihealth.datalib.request_bean.builder.InitiateVerificationBeanBuilder;
import com.kaurihealth.datalib.request_bean.builder.NewRegisterBeanBuilder;
import com.kaurihealth.utilslib.constant.Global;
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
 * Created by mip on 2016/8/10.
 */
public class RegisterPresenter<V> implements IRegisterPresenter<V> {

    private Repository mRepository;
    private IRegisterView mActivity;
    private CompositeSubscription mSubscriptions;

    @Inject
    public RegisterPresenter(Repository repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(Object view) {
        mActivity = (IRegisterView) view;
    }

    @Override
    public void onSubscribe() {
        NewRegisterBean newRegisterBean = getNewRegisterBean();

        Subscription subscribe = mRepository.userRegister(newRegisterBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RegisterResponse>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                        mActivity.switchPageUI(Global.Jump.RegisterPersonInfoActivity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterResponse registerResponse) {

                    }
                });
        mSubscriptions.add(subscribe);
    }

    private NewRegisterBean getNewRegisterBean() {
        String phoneNumber = mActivity.getPhoneNumber();
        String passWord = mActivity.getPassword();
        String verificationCode = mActivity.getVerificationCode();
        return new NewRegisterBeanBuilder().Build(phoneNumber, passWord, verificationCode);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    /**
     * 发送请求码并得到验证码
     */
    public void sendVerificationCodeRequest() {
        String phoneNumber = mActivity.getPhoneNumber();
        if (phoneNumber.matches("\\d{11,}")) {
            InitiateVerificationBean initiateVerificationBean = new InitiateVerificationBeanBuilder().BuildRegisterInitiateVerification(phoneNumber);
            //发送请求
            getVerificationCode(initiateVerificationBean);
            mActivity.startCountDown();
        } else {
            mActivity.showPhoneNumberErrorMessage();
        }
    }


    /**
     * 发送请求
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
                        LogUtils.e("Fail", e.getMessage());
                    }

                    @Override
                    public void onNext(InitiateVerificationResponse initiateVerificationResponse) {
                        LogUtils.e("Success", initiateVerificationResponse.message);
                    }
                });
        mSubscriptions.add(subscribe);
    }
}
