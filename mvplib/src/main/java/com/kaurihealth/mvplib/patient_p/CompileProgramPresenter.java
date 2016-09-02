package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;

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
public class CompileProgramPresenter<V> implements ICompileProgramPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ICompileProgramView mActivity;

    @Inject
    CompileProgramPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ICompileProgramView) view;
    }

    /**
     * 创建普通病人关系
     */
    @Override
    public void onSubscribe() {
        List<LongTermMonitoringDisplayBean> list = mActivity.getNewLongtermMonitoringBean();
        Subscription subscription = mRepository.updateLongtermMonitorings(list)
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
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<LongTermMonitoringDisplayBean> bean) {
                        mActivity.showToast("更新成功");
                        mActivity.dismissInteractionDialog();

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
