package com.kaurihealth.mvplib.login_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.InitiateVerificationBean;
import com.kaurihealth.datalib.request_bean.bean.LoginBean;
import com.kaurihealth.datalib.request_bean.builder.InitiateVerificationBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.InitiateVerificationResponse;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.utilslib.RegularUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
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
     * 登录
     */
    @Override
    public void onSubscribe() {
        LoginBean loginBean = mActivity.createRequestBean();
        Subscription subscription = mRepository.userLogin(loginBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    mActivity.dataInteractionDialog();
                    mActivity.setLoginBtnEnable(false);
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TokenBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.setLoginBtnEnable(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage().contains("Unauthorized") ? "用户名或密码错误" : e.getMessage());
                        mActivity.loginError(e);
                    }

                    @Override
                    public void onNext(TokenBean tokenBean) {
                        tokenBeanValidation(tokenBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 医生/患者
     */
    @Override
    public void tokenBeanValidation(TokenBean bean) {
        if (bean != null) {
            if (bean.getUser() != null && bean.getUser().getUserType().equals("患者")) {
                mActivity.displayErrorDialog("当前为患者账号,请输入医生账号重新登录");
            } else {
                mActivity.dismissInteractionDialog();

                percentageOfJudgment(bean);
                //将登陆成功之后，将用户名字保存起来
                mActivity.saveUsername();
            }
        }
    }

    @Override
    public void percentageOfJudgment(TokenBean tokenBean) {
        LocalData.getLocalData().setTokenBean(tokenBean);
        UserBean userBean = tokenBean.getUser();

        if (userBean != null) {
            if (userBean.getRegistPercentage() < 20) {
                mActivity.completeRegister();
            } else if (userBean.getRegistPercentage() < 30) {
                loadDoctorDetail();
                mActivity.unauthorizedPersonnel(tokenBean);
            } else {
                loadDoctorDetail();
                mActivity.initChatKitOpen(tokenBean);
            }
        } else {
            mActivity.completeRegister();
        }
    }

    /**
     * 医生详情
     */
    @Override
    public void loadDoctorDetail() {
        Subscription subscription = mRepository.loadDoctorDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<DoctorDisplayBean>() {
                            @Override
                            public void call(DoctorDisplayBean doctorDisplayBean) {
                                LocalData.getLocalData().setMyself(doctorDisplayBean);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mActivity.showToast(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 发送验证码
     */
    @Override
    public void sendVerificationCodeRequest() {
        String phoneNumber = mActivity.getUserName();   //用户名 == 电话
        if (RegularUtils.matcherMyContent("^1[3|4|5|7|8]\\d{9}$", phoneNumber)) {
            InitiateVerificationBean initiateVerificationBean = new InitiateVerificationBeanBuilder().BuildPhoneLoginInitiateVerification(phoneNumber);
            getVerificationCode(initiateVerificationBean);  //要求短信中心放松验证码
            mActivity.startCountDown();
        } else {
            //提示: 请输入正确的手机号码
            mActivity.showPhoneNumberErrorMessage();
        }
    }

    /**
     * 要求短信中心发送验证码
     */
    private void getVerificationCode(InitiateVerificationBean verificationBean) {
        Subscription subscribe = mRepository.InitiateVerification(verificationBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InitiateVerificationResponse>() {
                    @Override
                    public void onCompleted() {
//TODO 无
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

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

}
