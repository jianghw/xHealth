package com.kaurihealth.mvplib.patient_p;

import android.support.annotation.NonNull;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBeanWrapper;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
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
 * Created by Nick on 22/09/2016.
 */
public class FamilyMembersPresenter<V> implements IFamilyMembersPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IFamilyMembersView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    FamilyMembersPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (IFamilyMembersView) view;
    }


    //Step1: 拉取家庭成员的列表
    @Override
    public void onSubscribe() {
        int patientId = mActivity.getPatientId();
        Subscription subscription = mRepository.loadAllFamilyMembers(patientId)     //服务器返回数据:List<FamilyMemberBean>
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))  //刷新
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<FamilyMemberBean>>() {
                    @Override
                    public void call(List<FamilyMemberBean> familyMemberList) {
                        showAlreadyPatient(familyMemberList);  //Step2: 查询已经成为本人患者的patient
                    }
                }, new Action1<Throwable>() {  //相当于onError
                    @Override
                    public void call(Throwable throwable) {
                        //停止刷新
                        mActivity.loadingIndicator(false);
                        mActivity.showToast(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }


    //Step2:过滤准备 查询已经成为本人患者的patient  -->PatientFragment的API loadDoctorPatientRelationshipForDoctor   包含过期的患者(要进行过滤)
    @Override
    public void showAlreadyPatient(List<FamilyMemberBean> familyMemberList) {
        //查询本人所有的患者
        Subscription subscription = mRepository.loadDoctorPatientRelationshipForDoctor()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Subscriber<List<DoctorPatientRelationshipBean>>() {
                            @Override
                            public void onCompleted() {
                                mActivity.loadingIndicator(false);
                                mFirstLoad = false;
                            }

                            @Override
                            public void onError(Throwable e) {
                                mActivity.loadingIndicator(false);
                                mActivity.showToast(e.getMessage());
                            }

                            @Override
                            public void onNext(List<DoctorPatientRelationshipBean> list) {
                                //Step2.1 本地处理数据 避免重复服务
                                priorityDataArrangement(list,familyMemberList);
                            }
                        });

        mSubscriptions.add(subscription);
    }

    /**
     * Step2.1 : 过滤掉同一患者的重复服务
     * 过滤历史数据, 只要最新的数据, 之前的数据不要
     * @param list
     */
    private void priorityDataArrangement(List<DoctorPatientRelationshipBean> list,List<FamilyMemberBean> familyMemberList) {
        List<String> arrayList = new ArrayList<>();
        if (list == null) return;
        Subscription subscription = Observable.from(list)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        String key = getStringKeyToMap(doctorPatientRelationshipBean);
                        return !arrayList.contains(key);
                    }
                })
                .map(new Func1<DoctorPatientRelationshipBean, DoctorPatientRelationshipBean>() {
                    @Override
                    public DoctorPatientRelationshipBean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        String key = getStringKeyToMap(doctorPatientRelationshipBean);
                        arrayList.add(key);
                        return doctorPatientRelationshipBean;
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程
                .subscribe(
                        new Subscriber<List<DoctorPatientRelationshipBean>>() {
                            @Override
                            public void onCompleted() {
                                if (!arrayList.isEmpty()) arrayList.clear();
                            }

                            @Override
                            public void onError(Throwable e) {
                                mActivity.showToast(e.getMessage());
                                LogUtils.e(e.getMessage());
                            }

                            @Override
                            public void onNext(List<DoctorPatientRelationshipBean> list) {
                                //Step2.2  过滤出活动的  isActive = true
                                listFilteringActiveProcessing(list,familyMemberList);
                            }
                        });
        mSubscriptions.add(subscription);


    }

    /**
     * Step2.2 过滤出活动的患者-->相当于医疗团队里的第二步 : 需要List<familyMeberBean>
      * @param beanlist
     */
    private void listFilteringActiveProcessing(List<DoctorPatientRelationshipBean> beanlist,List<FamilyMemberBean> familyMemberList) {
        if (beanlist == null) return;
        Subscription subscription = Observable.from(beanlist)
                .subscribeOn(Schedulers.computation())
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean bean) {
                        return bean != null;
                    }
                })
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
                        return DateUtils.isActive(doctorPatientRelationshipBean.isIsActive(), doctorPatientRelationshipBean.getEndDate());
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<DoctorPatientRelationshipBean>>() {
                            @Override
                            public void call(List<DoctorPatientRelationshipBean> list) {
                                if (list == null || list != null && list.size()<1){
                                    //Step 3.1 手动处理数据
                                    manualProcessingData(familyMemberList);
                                }else{
                                    //Step 3.2: 1.将患者的家庭成员FamilyMember服务器返回的集合转包装类FamilyMemberBeanWrapper    2.逻辑判断 : 是否已经是患者 isPatient字段封装
                                    pateintProcessingData(familyMemberList,list);
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mActivity.showToast(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }


    //Step 3.1 手动处理数据-->包装类
    @Override
    public void manualProcessingData(List<FamilyMemberBean> familyMemberList) {
        Subscription subscription = Observable.from(familyMemberList)
                .map(new Func1<FamilyMemberBean, FamilyMemberBeanWrapper>() {
                    @Override
                    public FamilyMemberBeanWrapper call(FamilyMemberBean familyMemberBean) {
                        return new FamilyMemberBeanWrapper(familyMemberBean, "路人");   //服务器返回数据familyMemberBean +"路人" 包装成FamilyMemberBeanWrapper
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FamilyMemberBeanWrapper>>() {
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
                    public void onNext(List<FamilyMemberBeanWrapper> familyMemberBeanWrappers) {
                        mActivity.lazyLoadingDataSuccess(familyMemberBeanWrappers);   //将返回数据进行view层显示
                    }
                });
        mSubscriptions.add(subscription);

    }


    /**Step 3.2
     * 转包装类, 数据处理
     * @param familyMemberList
     * @param doctorList
     * 1.转map
     * 2.转包装类
     */
    @Override
    public void pateintProcessingData(List<FamilyMemberBean> familyMemberList, List<DoctorPatientRelationshipBean> doctorList) {
        Subscription subscription = Observable.from(doctorList)
                .filter(new Func1<DoctorPatientRelationshipBean, Boolean>() {
                    @Override
                    public Boolean call(DoctorPatientRelationshipBean relationshipBean) {
                        return relationshipBean != null;
                    }
                })
                .toMap(new Func1<DoctorPatientRelationshipBean, Integer>() {
                    @Override
                    public Integer call(DoctorPatientRelationshipBean relationshipBean) {
                        return relationshipBean.getPatientId();     //得到患者的id
                    }
                })
                .flatMap(new Func1<Map<Integer, DoctorPatientRelationshipBean>, Observable<List<FamilyMemberBeanWrapper>>>() {
                    @Override
                    public Observable<List<FamilyMemberBeanWrapper>> call(Map<Integer, DoctorPatientRelationshipBean> map) {
                        return Observable.from(familyMemberList)  //返回Observable 进行链式编程
                                .filter(new Func1<FamilyMemberBean, Boolean>() {
                                    @Override
                                    public Boolean call(FamilyMemberBean bean) {
                                        return bean != null;
                                    }
                                })
                                .filter(new Func1<FamilyMemberBean, Boolean>() {
                                    @Override
                                    public Boolean call(FamilyMemberBean familyMemberBean) {
                                        return familyMemberBean.getPatient() != null;
                                    }
                                })
                                .map(new Func1<FamilyMemberBean, FamilyMemberBeanWrapper>() {
                                    @Override
                                    public FamilyMemberBeanWrapper call(FamilyMemberBean bean) {
                                        //TODO 逻辑判断是否已经已经是患者
                                        boolean isPatient = map.containsKey(bean.getPatient().getPatientId());
                                        //familyMemberBean 进行装箱操作    relationship关系（0：普通，1：专属，2：转诊，3：协作）
                                        return new FamilyMemberBeanWrapper(bean, isPatient ? map.get(bean.getPatient().getPatientId()).getRelationship() : "路人");
                                    }
                                })
                                .toList();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<FamilyMemberBeanWrapper>>() {
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
                    public void onNext(List<FamilyMemberBeanWrapper> familyMemberBeanWrappers) {
                        mActivity.lazyLoadingDataSuccess(familyMemberBeanWrappers);   // 将familymeberwrapper 通过view层展示

                    }
                });
        mSubscriptions.add(subscription);
    }


    //点击"添加患者" 请求API
    @Override
    public void insertNewRelationshipByDoctor(int patientId) {
        Subscription subscription = mRepository.insertNewRelationshipByDoctor(patientId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorPatientRelationshipBean>() {
                    @Override
                    public void onCompleted() {
                        //b不要写
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(DoctorPatientRelationshipBean bean) {
                        mActivity.insertPatientSucceed(bean);
                        mActivity.dismissInteractionDialog();
                    }
                });
        mSubscriptions.add(subscription);

    }


    @NonNull
    private String getStringKeyToMap(DoctorPatientRelationshipBean bean) {
        int mPatientID = bean.getPatientId();
        String mShipReason = bean.getRelationshipReason();
        return mPatientID + mShipReason;
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        if (mActivity != null) mActivity.loadingIndicator(false);
        mActivity = null;
    }



}
