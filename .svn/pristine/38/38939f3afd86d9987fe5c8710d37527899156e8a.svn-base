package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/9/9.
 */
public class HomePresenter<T>  implements  IHomePresenter <T> {
    private  final Repository mRepository;
    private IHomeView mActivity ;
    private final CompositeSubscription mSubscriptions;


    @Inject
    HomePresenter(Repository repository){
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    //IMvpPresenter 设置Presenter
    @Override
    public void setPresenter(T view) {
        mActivity = (IHomeView) view;
    }

    //IMvpPresenter 网络请求数据
    @Override
    public void onSubscribe() {
         //查看病人发过来的请求
        Subscription subscription = mRepository.LoadPatientRequestsByDoctor()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();    //sweetDialog显示"正在加载中....."
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRequestDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PatientRequestDisplayBean> patientRequestDisplayBeen) {
                        //刷新ListView
                         mActivity.refreshListView(patientRequestDisplayBeen);

                    }
                });
        mSubscriptions.add(subscription);

    }

    //IMvpPresenter  解除绑定
    @Override
    public void unSubscribe() {
    }
}
