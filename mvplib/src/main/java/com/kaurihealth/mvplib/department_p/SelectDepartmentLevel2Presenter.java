package com.kaurihealth.mvplib.department_p;


import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/9/6.
 * <p/>
 * 描述：
 */
public class SelectDepartmentLevel2Presenter<V> implements  ISelectDepartmentLevel2Presenter<V> {
    private  final IDataSource mRepository;
    private  final CompositeSubscription mSubscriptions;
    private  ISelectDepartmentLevel2View mActivity;

    @Inject
    SelectDepartmentLevel2Presenter(IDataSource repository){
        mRepository = repository ;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ISelectDepartmentLevel2View)view;
    }

    //显示科室的子集目录:第二次去网络请求数据
    @Override
    public void onSubscribe() {
        //拿到父类的parentDepartmentId字段注入方法中
        Integer parentDepartmentId = mActivity.getParentDepartmentId();
        int level = 2;
        Subscription subscription = mRepository.loadDepartmentName(level , parentDepartmentId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread()) //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DepartmentDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayError(e);
                    }

                    @Override
                    public void onNext(List<DepartmentDisplayBean> list) {
                        //刷新二级科室
                        mActivity.refreshListView(list);
                    }
                });
        mSubscriptions.add(subscription);
    }

    //解除订阅
    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }

    public void upDateDoctor() {
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
                        mActivity.returnBundle();
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
}
