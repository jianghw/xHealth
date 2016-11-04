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
 * Created by Nick on 30/08/2016.
 */
public class EnterStudyExperiencePresenter<V> implements IEnterStudyExperiencePresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IEnterStudyExperienceView mActivity;

    @Inject
    EnterStudyExperiencePresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IEnterStudyExperienceView) view;
    }

    @Override
    public void onSubscribe() {
        final DoctorDisplayBean myself = mActivity.getDoctorDisplayBean();
        //调用repository
        Subscription subscription = mRepository.updateDoctor(myself)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread()) //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.showToast("修改成功!");
                        mActivity.switchPageUI("");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean doctorDisplayBean) {
                        mActivity.dismissInteractionDialog();
                        LocalData.getLocalData().setMyself(doctorDisplayBean);
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
