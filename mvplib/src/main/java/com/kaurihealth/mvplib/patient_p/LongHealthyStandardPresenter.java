package com.kaurihealth.mvplib.patient_p;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

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
public class LongHealthyStandardPresenter<V> implements ILongHealthyStandardPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ILongHealthyStandardView mActivity;

    @Inject
    LongHealthyStandardPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ILongHealthyStandardView) view;
    }

    /**
     * 创建普通病人关系
     */
    @Override
    public void onSubscribe() {
        DoctorPatientRelationshipBean bean = mActivity.getDoctorPatientRelationshipBean();
        Subscription subscription = mRepository.longTermMonitoringDisplay(bean.getPatientId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog(); //正在加载中...
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LongTermMonitoringDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<LongTermMonitoringDisplayBean> list) {
                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                                .create();
                        LogUtils.json(gson.toJson(list));
                        if (list.size() == 0) mActivity.showToast("你还未添加过监测指标");
                        mActivity.drawChartsByList(list);
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
