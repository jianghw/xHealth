package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewFamilyMemberBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.main_p.MainPresenter;

import java.util.IdentityHashMap;

import javax.inject.Inject;

import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/11/10.
 */
public class AddFamilyMemberPresenter<V> implements  IAddFamilyMemberPresenter<V>  {
    private  final IDataSource mRepository;
    private CompositeSubscription  mSubscriptions;
    private  IAddFamilyMemberView mActivity;

    @Inject
    AddFamilyMemberPresenter(IDataSource repository){
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    //IMvpPresenter
    @Override
    public void setPresenter(V view) {
        mActivity = (IAddFamilyMemberView)view;
    }

    //IMvpPresenter
    @Override
    public void onSubscribe() {
        //请求bean
        NewFamilyMemberBean newFamilyMemberBean = mActivity.getNewFamilyMemberBean();
        Subscription subscription = mRepository.addFamilyMemberByDoctor(newFamilyMemberBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();   //sweetDialog显示"正在加载中..."
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        //返回数据要进行判断是否成功
                        if (responseDisplayBean.isIsSucess()) {  //success
                            mActivity.AddFamilyMemberSuccess();
                        } else {
                            mActivity.showToast(responseDisplayBean.getMessage());
                            mActivity.displayErrorDialog(responseDisplayBean.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscription);

    }

    //IMvpPresenter
    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
