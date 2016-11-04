package com.kaurihealth.mvplib.main_p;

import android.graphics.Bitmap;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.utilslib.image.FastBlurUtil;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 23/08/2016.
 */
public class MinePresenter<V> implements IMinePresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IMineView mFragment;
    private boolean mFirstLoad = true;

    @Inject
    MinePresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IMineView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadDoctorDetail(false);
    }

    @Override
    public void loadDoctorDetail(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }
        Subscription subscription = mRepository.loadDoctorDetail()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mFragment.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mFirstLoad = false;
                        mFragment.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.loadingIndicator(false);
                        mFragment.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean doctorDisplayBean) {
                        LocalData.getLocalData().setMyself(doctorDisplayBean);
                        mFragment.loadDoctorDataSucceed(doctorDisplayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void gaussianBlur(String avatar) {
        Subscription subscription = Observable.create(
                new Observable.OnSubscribe<Bitmap>() {
                    @Override
                    public void call(Subscriber<? super Bitmap> subscriber) {
                        try {
                            Bitmap blurBitmap2 = FastBlurUtil.GetUrlBitmap(avatar, 5);
                            subscriber.onNext(blurBitmap2);
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            subscriber.onError(e);
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        mFragment.gaussianBlur(bitmap);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment.loadingIndicator(false);
        mFragment = null;
    }
}
