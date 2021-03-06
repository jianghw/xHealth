package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.RelationshipBeanWrapper;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 1/09/2016.
 * <p/>
 * Describe:
 */
public class DoctorTeamPresenter<V> implements IDoctorTeamPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IDoctorTeamView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    DoctorTeamPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IDoctorTeamView) view;
    }


    /**
     * 使用患者id查询医疗团队，查询者必须为医生
     */
    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(true);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) {//刷新
            mRepository.manuallyRefresh();
        }
        loadDoctorTeamForPatient();
    }

    private void loadDoctorTeamForPatient() {
        int patientId = mActivity.getPatientId();
        Subscription subscription = mRepository.loadDoctorTeamForPatient(patientId)  //服务器返回数据:List<DoctorPatientRelationshipBean>
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DoctorPatientRelationshipBean>>() {
                    @Override
                    public void call(List<DoctorPatientRelationshipBean> beanList) {   //使用患者id查询医疗团队
                        showCooperationDoctor(beanList);    //查询已经和自己成为协作医生的医生
                    }
                }, new Action1<Throwable>() {   //相当于onError
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }


    //查询已经和自己成为协作医生的医生
    @Override
    public void showCooperationDoctor(List<DoctorPatientRelationshipBean> beanList) {  //外传进来的
        Subscription subscription = mRepository.loadAllDoctorRelationships()   //查询用户所有医生关系的病例(包含拒绝的)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<DoctorRelationshipBean>>() {
                    @Override
                    public void call(List<DoctorRelationshipBean> list) {
                        mFirstLoad = true;
                        if (list == null || list.size() < 1) {
                            manualProcessingData(beanList);
                        } else {
                            //1.将本人协作医生服务器返回的集合转包装类RelationshipBeanWrapper    2.逻辑判断 : 是否已经是协作医生 isFriend字段封装
                            doctorProcessingData(beanList, list);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void manualProcessingData(List<DoctorPatientRelationshipBean> relationshipBeen) {
        Subscription subscription = Observable.from(relationshipBeen)
                .map(new Func1<DoctorPatientRelationshipBean, RelationshipBeanWrapper>() {
                    @Override
                    public RelationshipBeanWrapper call(DoctorPatientRelationshipBean bean) {
                        return new RelationshipBeanWrapper(bean, "路人");  //服务器返回的数据,包装"路人"字段
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RelationshipBeanWrapper>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<RelationshipBeanWrapper> wrappers) {
                        mActivity.lazyLoadingDataSuccess(wrappers);   //返回数据进行view层展示
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 1、转map
     * 2、转包装类
     */
    @Override
    public void doctorProcessingData(List<DoctorPatientRelationshipBean> relationshipBeen, List<DoctorRelationshipBean> beanList) {
        Subscription subscription = Observable.from(beanList)
                .filter(new Func1<DoctorRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorRelationshipBean bean) {
                        return bean != null;
                    }
                })
                .toMap(new Func1<DoctorRelationshipBean, Integer>() {
                    @Override
                    public Integer call(DoctorRelationshipBean bean) {
//                        return bean.getDestinationDoctorId();
//                        return bean.getSourceDoctorId();
                        return bean.getRelatedDoctor().getDoctorId();

                    }
                })
                .flatMap(new Func1<Map<Integer, DoctorRelationshipBean>, Observable<List<RelationshipBeanWrapper>>>() {
                    @Override
                    public Observable<List<RelationshipBeanWrapper>> call(Map<Integer, DoctorRelationshipBean> map) {
                        return Observable.from(relationshipBeen)   //返回Observable 又可以进行连点操作,对返回的结果继续观察
                                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                                    @Override
                                    public Boolean call(DoctorPatientRelationshipBean bean) {
                                        return bean != null;
                                    }
                                })
                                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                                    @Override
                                    public Boolean call(DoctorPatientRelationshipBean bean) {
                                        return bean.getDoctor() != null;
                                    }
                                })
                                .map(new Func1<DoctorPatientRelationshipBean, RelationshipBeanWrapper>() {
                                    @Override
                                    public RelationshipBeanWrapper call(DoctorPatientRelationshipBean bean) {

                                        boolean isFriend = map.containsKey(bean.getDoctor().getDoctorId());
                                        //status 状态（0：等待。1：接受，2：拒绝）
                                        return new RelationshipBeanWrapper(bean, isFriend ? map.get(bean.getDoctor().getDoctorId()).getStatus() : "路人");
                                    }
                                })
                                .toList();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RelationshipBeanWrapper>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<RelationshipBeanWrapper> wrappers) {
                        mActivity.lazyLoadingDataSuccess(wrappers);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.loadingIndicator(false);
        mSubscriptions.clear();
        mActivity = null;
    }
}
