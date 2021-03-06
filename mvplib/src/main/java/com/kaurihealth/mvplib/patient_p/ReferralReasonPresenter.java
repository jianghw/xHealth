package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jhw on 2016/10/25.
 */

public class ReferralReasonPresenter<V> implements IReferralReasonPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IReferralReasonView mActivity;

    @Inject
    public ReferralReasonPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IReferralReasonView) view;
    }

    /**
     * 向多个医生转诊患者
     */
    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.InsertPatientRequestReferralByDoctorList(
                mActivity.getPatientRequestReferralDoctorDisplayBean())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseDisplayBean>() {
                    @Override
                    public void call(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
                            mActivity.insertPatientRequestReferralByDoctorListSucceed(bean);
                        } else {
                            mActivity.displayErrorDialog(bean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 向一个医生转诊多个患者
     */
    public void InsertPatientRequestReferralByPatientList() {
        Subscription subscription = mRepository.InsertPatientRequestReferralByPatientList(mActivity.getPatientRequestBean())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ResponseDisplayBean>() {
                    @Override
                    public void call(ResponseDisplayBean bean) {
                        if (bean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
                            mActivity.insertPatientRequestReferralByDoctorListSucceed(bean);
                        } else {
                            mActivity.displayErrorDialog(bean.getMessage());
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.dismissInteractionDialog();
        mSubscriptions.clear();
        mActivity = null;
    }

    @Override
    public void createGroupChat(ArrayList<DoctorRelationshipBean> list) {
        Subscription subscription = Observable.from(list)
                .filter(new Func1<DoctorRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorRelationshipBean relationshipBean) {
                        return null != relationshipBean;
                    }
                })
                .map(new Func1<DoctorRelationshipBean, DoctorDisplayBean>() {
                    @Override
                    public DoctorDisplayBean call(DoctorRelationshipBean relationshipBean) {
                        return relationshipBean.getRelatedDoctor();
                    }
                })
                .filter(new Func1<DoctorDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorDisplayBean displayBean) {
                        return null != displayBean;
                    }
                })
                .map(new Func1<DoctorDisplayBean, String>() {
                    @Override
                    public String call(DoctorDisplayBean displayBean) {
                        return displayBean.getKauriHealthId();
                    }
                })
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<String>>() {
                    @Override
                    public void call(List<String> strings) {
                        ArrayList<String> mList = new ArrayList<>();
                        if (strings != null) mList.addAll(strings);
                        mActivity.createGroupChatSucceed(mList);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void loadDoctorMainPage() {
        Subscription subscription = mRepository.loadDoctorMainPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<List<MainPagePatientDisplayBean>>() {
                            @Override
                            public void onCompleted() {
                                LogUtils.d("loadDoctorMainPage");
                            }

                            @Override
                            public void onError(Throwable e) {
                                mActivity.showToast(e.getMessage());
                            }

                            @Override
                            public void onNext(List<MainPagePatientDisplayBean> list) {
                                mActivity.loadDoctorMainPageSucceed(list);
                            }
                        });
        mSubscriptions.add(subscription);
    }
}
