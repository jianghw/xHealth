package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.date.RemainTimeBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：登录逻辑
 */
public class PatientInfoPresenter<V> implements IPatientInfoPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IPatientInfoView mActivity;

    @Inject
    PatientInfoPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IPatientInfoView) view;
    }

    /**
     * 创建普通病人关系
     */
    @Override
    public void onSubscribe() {
        DoctorPatientRelationshipBean bean = mActivity.getDoctorPatientRelationshipBean();
        Subscription subscription = mRepository.insertNewRelationshipByDoctor(bean.getPatientId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog(); //正在加载中...
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorPatientRelationshipBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorPatientRelationshipBean bean) {
                        mActivity.showToast("复诊成功");
                        mActivity.dismissInteractionDialog();
                        mActivity.updateBeanData(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    @Override
    public void startCountdown(final Date endDate) {
        mSubscriptions.clear();
        Subscription subCount = Observable.interval(1, 1000, TimeUnit.MILLISECONDS)
                .map(new Func1<Long, RemainTimeBean>() {
                    @Override
                    public RemainTimeBean call(Long aLong) {
                        return DateUtils.getEndTimeBean(endDate);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<RemainTimeBean>() {
                            @Override
                            public void call(RemainTimeBean bean) {
                                mActivity.showCountdown(bean);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subCount);
    }
}
