package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/11/29.
 * <p/>
 * Describe:
 */

public class GroupDetailsPresenter<V> implements IGroupDetailsPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IGroupDatailsView activity;

    @Inject
    public GroupDetailsPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        activity = (IGroupDatailsView) view;
    }

    @Override
    public void onSubscribe() {
        LoadDoctorDetailByDoctorId();
    }

    public void LoadDoctorDetailByDoctorId(){//通过id查询医生信息
        LogUtils.e("doctorId",activity.getDoctorId()+"");
        Subscription subscription = mRepository.LoadDoctorDetailByDoctorId(activity.getDoctorId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> activity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    activity.dismissInteractionDialog();
                        activity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorDisplayBean bean) {
                        priorityData(bean);
                        LogUtils.jsonDate(bean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 过滤
     */
    private void priorityData(DoctorDisplayBean bean) {
        //查询所有用户医生关系的病例（包含拒绝的） 医生使用的token
        Subscription subscription = mRepository.loadAllDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        activity.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorRelationshipBean> beanList) {
                        //将同意和自己成为协作医生的doctor过滤出来
                        priorityDataArrangement(beanList,bean);
                        LogUtils.jsonDate(beanList);
                    }
                });
        mSubscriptions.add(subscription);

    }


    /**
     * 过滤
     * @param list
     */
    private void priorityDataArrangement(List<DoctorRelationshipBean> list,DoctorDisplayBean displayBean) {
        String kuariHealthId = activity.getCurKuariHealthId();
        if (list == null || list.isEmpty()) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(doctorRelationshipBean -> doctorRelationshipBean.getStatus().equals("接受"))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        doctorRelationshipBeen -> {
                            LogUtils.jsonDate(doctorRelationshipBeen);
                            boolean isHasDate = true;
                            for (DoctorRelationshipBean bean : doctorRelationshipBeen) {
                                if (bean.getRelatedDoctor().getKauriHealthId().equals(kuariHealthId)) {
                                    activity.getResult(bean,true);//将匹配到的数据发回
                                    isHasDate = false;
                                }
                            }
                            //判断是否有自己的协作医生，若没有则發送未接受的
                            if (!isHasDate) activity.dismissInteractionDialog();
                            if (isHasDate) priorityNotReceptDataArrangement(displayBean);

                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                LogUtils.e(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 包装不是自己的协作医生
     */
    private void priorityNotReceptDataArrangement(DoctorDisplayBean bean) {
        DoctorRelationshipBean doctorRelationshipBean = new DoctorRelationshipBean();
        doctorRelationshipBean.setRelatedDoctor(bean);
        activity.dismissInteractionDialog();
        activity.getResult(doctorRelationshipBean,false);


    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
