package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 31/08/2016.
 */
public class EnterHospitalPresenter<V> implements IEnterHospitalPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IEnterHospitalView mActivity;

    @Inject
    EnterHospitalPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IEnterHospitalView) view;
    }

    @Override
    public void onSubscribe() {
        DoctorDisplayBean myself = mActivity.getDoctorDisplayBean();
        Subscription subscription = mRepository.updateDoctor(myself)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())  //只可以在主线程运行
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


    //获得所有医院的名称
    @Override
    public void getAllHospitalName() {
        Subscription subscription = mRepository.LoadAllHospitals()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())        //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<String> strings) {
                        mActivity.refreshListView(strings);
                    }
                });
        mSubscriptions.add(subscription);
    }
}
