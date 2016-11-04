package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/7.
 */
public class HospitalNamePresenter<V> implements IHospitalNamePresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IHospitalNameView mActivity;

    @Inject
    public HospitalNamePresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        this.mActivity = (IHospitalNameView) view;
    }

    @Override
    public void onSubscribe() {

        Subscription subscription = mRepository.LoadAllHospitals()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())        //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.showToast("获取数据失败!");
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        //将服务器返回的strings 放入list维持
                        mActivity.getHospitalNameList(strings);
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
