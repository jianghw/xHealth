package com.kaurihealth.mvplib.patient_p;

import android.support.annotation.NonNull;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
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
        int patientId = mActivity.getPatientId();
        Subscription subscription = mRepository.longTermMonitoringDisplay(patientId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    mActivity.dataInteractionDialog(); //正在加载中...
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
                        if (list.size() == 0) mActivity.showToast("你还未添加过监测指标");
                        mActivity.loadingDataSuccess(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * dialog所需数据
     * 1、获取type
     * 2、把舒张压或收缩压转 血压
     * 3、去重复
     * 4、转list
     * 5、转string[]
     */
    @Override
    public void dialogDateByList(List<LongTermMonitoringDisplayBean> list) {
        if (list.isEmpty()) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .map(LongTermMonitoringDisplayBean::getType)
                .map(this::getTypeToBlood)
                .distinct()
                .toList()
                .map(strings -> strings.toArray(new String[strings.size()]))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        strings -> {
                            mActivity.showDialogByString(strings);
                        },
                        throwable -> {
                            LogUtils.e(throwable.getMessage());
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * type to blood
     */
    @NonNull
    @Override
    public String getTypeToBlood(String type) {
        return type.equals(Global.Environment.BLOOD_DIASTOLIC) ? Global.Environment.BLOOD :
                type.equals(Global.Environment.BLOOD_SHRINKAGE) ? Global.Environment.BLOOD : type;
    }

    /**
     * 点击选出的对应数据
     * 1、过滤掉没选的数据
     * 2、转list
     * 3、转嵌套list
     *
     * @param type
     * @param beanList
     */
    @Override
    public void needToDisplayData(String type, List<LongTermMonitoringDisplayBean> beanList) {
        Subscription subscription = Observable.from(beanList)
                .subscribeOn(Schedulers.computation())
                .filter(bean -> typeFilteringRule(bean, type))
                .toList()
                .map(list -> {
                    List<List<LongTermMonitoringDisplayBean>> nestedList = new ArrayList<>();
                    if (type.equals(Global.Environment.BLOOD)) {
                        addGroupBloodData(list, nestedList, Global.Environment.BLOOD_SHRINKAGE);
                        addGroupBloodData(list, nestedList, Global.Environment.BLOOD_DIASTOLIC);
                    } else {
                        nestedList.add(list);
                    }
                    return nestedList;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        lists -> {
                            mActivity.distributionListData(lists);
                        },
                        throwable -> {
                            LogUtils.e(throwable.getMessage());
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 血压分组添加数据
     */
    @Override
    public void addGroupBloodData(List<LongTermMonitoringDisplayBean> list, List<List<LongTermMonitoringDisplayBean>> nestedList, String type) {
        Observable.from(list)
                .filter(longTermMonitoringDisplayBean -> longTermMonitoringDisplayBean.getType().equals(type))
                .toList()
                .subscribe(nestedList::add);
    }

    @Override
    public void onDeleteProgram(List<Integer> bean) {
        Subscription subscription = mRepository.deleteLongtermMonitorings(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> {
                    mActivity.dataInteractionDialog();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        if (responseDisplayBean.isIsSucess()) {
                            mActivity.showToast("删除成功~");
                            mActivity.dismissInteractionDialog();
                            mActivity.updateDataOnly();
                        } else {
                            mActivity.displayErrorDialog(responseDisplayBean.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 类型过滤规则
     */
    @Override
    public boolean typeFilteringRule(LongTermMonitoringDisplayBean bean, String type) {
        if (type.equals(Global.Environment.BLOOD)) {
            return bean.getType().equals(Global.Environment.BLOOD_SHRINKAGE) || bean.getType().equals(Global.Environment.BLOOD_DIASTOLIC);
        } else {
            return bean.getType().equals(type);
        }
    }


    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
