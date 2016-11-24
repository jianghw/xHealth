package com.kaurihealth.mvplib.register_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.utilslib.log.LogUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class RegisterPersonInfoPresenter<V> implements IRegisterPersonInfoPresenter<V> {

    private Repository mRepository;
    private IRegisterPersonInfoView mActivity;
    private CompositeSubscription mSubscriptions;

    @Inject
    public RegisterPersonInfoPresenter(Repository repository) {
        mSubscriptions = new CompositeSubscription();
        mRepository = repository;
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IRegisterPersonInfoView) view;
    }

    @Override
    public void onSubscribe() {
        DoctorUserBean doctorUserBean = mActivity.getDoctorUserBean();

        Subscription subscribe = mRepository.updateDoctorUserInformation(doctorUserBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog("保存失败,请联系管理员" + e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            modifiedRegisterPercentage();
                            loadDoctorDetail();
                            mActivity.connectLeanCloud();
                        } else {
                            mActivity.showToast(bean.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscribe);
    }

    /**
     * 修改寄存器的百分比
     */
    @Override
    public void modifiedRegisterPercentage() {
        Subscription subscribe = mRepository.persistentData(
               Observable.create(new rx.Observable.OnSubscribe<TokenBean>() {
                    @Override
                    public void call(Subscriber<? super TokenBean> subscriber) {
                        try {
                            TokenBean token = LocalData.getLocalData().getTokenBean();
                            token.getUser().setRegistPercentage(30);
                            subscriber.onNext(token);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TokenBean>() {
                               @Override
                               public void call(TokenBean tokenBean) {
                                   LocalData.getLocalData().setTokenBean(tokenBean);
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscribe);
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

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
