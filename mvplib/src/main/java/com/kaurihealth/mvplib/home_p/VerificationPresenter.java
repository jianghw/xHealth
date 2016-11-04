package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewDoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/9/19.
 */
public class VerificationPresenter<V> implements IVerificationPresenter<V>{

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IVerificationView mActivity;

    @Inject
    public VerificationPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IVerificationView) view;
    }

    @Override
    public void onSubscribe() {
        NewDoctorRelationshipBean bean = mActivity.getCurrentNewDoctorRelationshipBean();
        Subscription subscription = mRepository.RequestNewDoctorRelationship(bean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
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
                        if(responseDisplayBean.isIsSucess()){
                            mActivity.getRequestResult(responseDisplayBean);
                        }else{
                            mActivity.showToast("该医生已添加,请勿重复请求"+responseDisplayBean.getMessage());
                        }
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity=null;
    }
}
