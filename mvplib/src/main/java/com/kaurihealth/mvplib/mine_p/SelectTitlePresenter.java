package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 29/08/2016.
 */
public class SelectTitlePresenter<V> implements ISelectTitlePresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ISelectTitleView mActivity;

    @Inject
    SelectTitlePresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ISelectTitleView) view;
    }

    @Override
    public void onSubscribe() {
        DoctorDisplayBean myself = mActivity.getDoctorDisplayBean();
        Subscription subscription = mRepository.updateDoctor(myself)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                        mActivity.showToast("保存成功!");
                        mActivity.switchPageUI("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean myself) {
                        LocalData.getLocalData().setMyself(myself);
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
