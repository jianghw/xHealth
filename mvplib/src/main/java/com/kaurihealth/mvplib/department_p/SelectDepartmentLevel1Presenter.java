package com.kaurihealth.mvplib.department_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.utilslib.CheckUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/8/31.
 * <p/>
 * 描述：
 */
public class SelectDepartmentLevel1Presenter<V> implements ISelectDepartmentLevel1Presenter<V> {
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private ISelectDepartmentLevel1View mActivity;

    @Inject
    SelectDepartmentLevel1Presenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ISelectDepartmentLevel1View) view;
    }

    /*
     *一级
     */
    @Override
    public void onSubscribe() {
        Subscription subscription = mRepository.loadDepartment()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())    //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DepartmentDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DepartmentDisplayBean> departmentList) {
                        mActivity.refreshListView(departmentList);
                    }
                });
        mSubscriptions.add(subscription);
    }

    //查询子类科室
    @Override
    public void querySubclassDepartment(int level, DepartmentDisplayBean departmentDisplayBean) {
        //拿到父类的parentDepartmentId字段注入方法中
        CheckUtils.checkNullArgument(departmentDisplayBean, "departmentDisplayBean must can be null");
        Subscription subscription = mRepository.loadDepartmentName(level, departmentDisplayBean.getDepartmentId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread()) //只可以在主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DepartmentDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DepartmentDisplayBean> list) {
                        //刷新二级科室
                        mActivity.refreshListView(list);
                    }
                });
        mSubscriptions.add(subscription);
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

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;
    }
}
