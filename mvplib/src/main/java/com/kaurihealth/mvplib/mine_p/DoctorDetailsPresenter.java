package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DocumentDisplayBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 24/08/2016.
 */
public class DoctorDetailsPresenter<V> implements IDoctorDetailsPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IDoctorDetailsView mActivity;

    @Inject
    DoctorDetailsPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IDoctorDetailsView) view;
    }

    @Override
    public void onSubscribe() {
        DoctorDisplayBean myself = mActivity.getDoctorDisplayBean();
        //调用repository
        updateDoctorBean(myself);
    }

    private void updateDoctorBean(DoctorDisplayBean myself) {
        Subscription subscription = mRepository.updateDoctor(myself)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                        mActivity.showToast("修改成功!");
                        mActivity.switchPageUI("mineFrag");
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean myself) {
                        LocalData.getLocalData().setMyself(myself);
                        mActivity.renderView();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    /**
     * 更新头像
     *
     * @param paths
     */
    @Override
    public void updateAvatarDoctor(ArrayList<String> paths) {
        Subscription subscription = mRepository.getUploadListObservable(paths, LocalData.getLocalData().getTokenBean().getUser().getKauriHealthId())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog(); //正在加载中...
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DocumentDisplayBean>>() {
                    @Override
                    public void call(List<DocumentDisplayBean> beanList) {
                        DoctorDisplayBean bean = (DoctorDisplayBean) LocalData.getLocalData().getMyself().clone();
                        if (beanList != null && beanList.size() > 0) {
                            bean.setAvatar(beanList.get(0).getDocumentUrl());
                            updateDoctorBean(bean);
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
}
