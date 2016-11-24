package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.NewDoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/9/19.
 * <p/>
 * Describe:
 */
public class VerificationPresenter<V> implements IVerificationPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IVerificationView mActivity;

    @Inject
    public VerificationPresenter(IDataSource mRepository) {
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
                .subscribe(new Action1<ResponseDisplayBean>() {
                    @Override
                    public void call(ResponseDisplayBean responseDisplayBean) {
                        if (responseDisplayBean.isIsSucess()) {
                            mActivity.dismissInteractionDialog();
                            mActivity.getRequestResult(responseDisplayBean);
                        } else {
                            mActivity.displayErrorDialog("该医生已添加," + responseDisplayBean.getMessage());
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
        mSubscriptions.clear();
        if (mActivity != null) mActivity.dismissInteractionDialog();
        mActivity = null;
    }
}
