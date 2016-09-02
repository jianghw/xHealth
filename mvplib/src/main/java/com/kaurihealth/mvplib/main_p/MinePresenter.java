package com.kaurihealth.mvplib.main_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 23/08/2016.
 */
public class MinePresenter<V> implements IMinePresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IMineView mFragment;

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
        if (LocalData.getLocalData().getMyself() == null) {
            Subscription subscription = mRepository.loadDoctorDetail()
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<DoctorDisplayBean>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            mFragment.showErrorToast("医生读取失败.");
                        }

                        @Override
                        public void onNext(DoctorDisplayBean doctorDisplayBean) {
                            LocalData.getLocalData().setMyself(doctorDisplayBean);
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment = null;
    }
}
