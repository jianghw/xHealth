package com.kaurihealth.mvplib.sickness_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.LoadAllSicknessItem;
import com.kaurihealth.datalib.response_bean.AllSicknessDisplayBean;
import com.kaurihealth.datalib.response_bean.ChildDepartmentsBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.utilslib.log.LogUtils;

import java.util.ArrayList;
import java.util.List;

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
 * Created by jianghw on 2016/9/21.
 * <p/>
 * Describe:门诊诊疗
 */
public class LoadAllSicknessPresenter<V> implements ILoadAllSicknessPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ILoadAllSicknessView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    public LoadAllSicknessPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ILoadAllSicknessView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad) loadingRemoteData(false);
    }

    @Override
    public void loadingRemoteData(boolean isDirty) {
        if (isDirty) mRepository.manuallyRefresh();

        loadAllSicknessByDepartmentLevel();
    }

    /**
     * 按级读取科室以及疾病
     */
    private void loadAllSicknessByDepartmentLevel() {
        Subscription subscription = mRepository.loadAllSicknessByDepartmentLevel()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.loadingIndicator(true))
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<AllSicknessDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mFirstLoad = false;
                        mActivity.loadingIndicator(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFirstLoad = true;
                        mActivity.loadingIndicator(false);
                        mActivity.loadAllSicknessError();
                    }

                    @Override
                    public void onNext(List<AllSicknessDisplayBean> sicknessDisplayBeen) {
                        noChildDepartmentsData(sicknessDisplayBeen);
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void noChildDepartmentsData(List<AllSicknessDisplayBean> beanList) {
        Subscription subscription = Observable.from(beanList)
                .filter(new Func1<AllSicknessDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(AllSicknessDisplayBean bean) {
                        return null != bean;
                    }
                })
                .map(new Func1<AllSicknessDisplayBean, AllSicknessDisplayBean>() {
                    @Override
                    public AllSicknessDisplayBean call(AllSicknessDisplayBean bean) {
                        List<SicknessesBean> sicknessesBeanList = bean.getSicknesses();
                        if (bean.getChildDepartments() == null && sicknessesBeanList != null && sicknessesBeanList.size() > 0) {
                            List<ChildDepartmentsBean> list = new ArrayList<>();
                            SicknessesBean sicknessesBean = sicknessesBeanList.get(0);
                            ChildDepartmentsBean childDepartmentsBean = new ChildDepartmentsBean();
                            childDepartmentsBean.setDepartmentId(sicknessesBean.getDepartmentId());
                            childDepartmentsBean.setDepartmentName(sicknessesBean.getDepartmentName());
                            childDepartmentsBean.setLevel(2);
                            childDepartmentsBean.setSicknesses(bean.getSicknesses());
                            list.add(childDepartmentsBean);
                            bean.setChildDepartments(list);
                        }
                        return bean;
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AllSicknessDisplayBean>>() {
                    @Override
                    public void call(List<AllSicknessDisplayBean> sicknessDisplayBeen) {
                        mActivity.loadAllSicknessSucceed(sicknessDisplayBeen);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 更新右侧列表
     */
    @Override
    public void updateRightOptions(List<ChildDepartmentsBean> departments) {
        Subscription subscription = Observable.from(departments)
                .filter(new Func1<ChildDepartmentsBean, Boolean>() {
                    @Override
                    public Boolean call(ChildDepartmentsBean bean) {
                        return null != bean;
                    }
                })
                .map(new Func1<ChildDepartmentsBean, LoadAllSicknessItem>() {
                    @Override
                    public LoadAllSicknessItem call(ChildDepartmentsBean bean) {
                        return new LoadAllSicknessItem(bean.getDepartmentName(), bean.getSicknesses());
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<LoadAllSicknessItem>>() {
                    @Override
                    public void call(List<LoadAllSicknessItem> items) {
                        mActivity.updateRightOptionsSucceed(items);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 1、有相同的即 删除
     * 2、没相同 即添加
     */
    @Override
    public void selectedSickDiseases(List<SicknessesBean> list, SicknessesBean sicknessesBean) {
        if (list.isEmpty()) {
            List<SicknessesBean> beanList = new ArrayList<>();
            beanList.add(sicknessesBean);
            mActivity.selectedSickDiseasesSucceed(beanList);
        } else {
            Subscription subscription = Observable.from(list)
                    .filter(new Func1<SicknessesBean, Boolean>() {
                        @Override
                        public Boolean call(SicknessesBean bean) {
                            return null != bean;
                        }
                    })
                    .filter(new Func1<SicknessesBean, Boolean>() {
                        @Override
                        public Boolean call(SicknessesBean bean) {
                            return !bean.getSicknessName().equals(sicknessesBean.getSicknessName());
                        }
                    })
                    .toList()
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<SicknessesBean>>() {
                        @Override
                        public void call(List<SicknessesBean> been) {
                            if (been.size() == list.size()) been.add(sicknessesBean);
                            mActivity.selectedSickDiseasesSucceed(been);
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            LogUtils.e(throwable.getMessage());
                        }
                    });
            mSubscriptions.add(subscription);
        }
    }

    /**
     * 搜索疾病
     */
    @Override
    public void onSearchSickness(List<AllSicknessDisplayBean> been, CharSequence text) {
        Subscription subscription = Observable.from(been)
                .filter(new Func1<AllSicknessDisplayBean, Boolean>() {
                    @Override
                    public Boolean call(AllSicknessDisplayBean bean) {
                        return null != bean;
                    }
                })
                .map(new Func1<AllSicknessDisplayBean, List<ChildDepartmentsBean>>() {
                    @Override
                    public List<ChildDepartmentsBean> call(AllSicknessDisplayBean bean) {
                        return bean.getChildDepartments();
                    }
                })
                .filter(new Func1<List<ChildDepartmentsBean>, Boolean>() {
                    @Override
                    public Boolean call(List<ChildDepartmentsBean> been) {
                        return null != been;
                    }
                })
                .flatMap(new Func1<List<ChildDepartmentsBean>, Observable<ChildDepartmentsBean>>() {
                    @Override
                    public Observable<ChildDepartmentsBean> call(List<ChildDepartmentsBean> been) {
                        return Observable
                                .from(been)
                                .filter(new Func1<ChildDepartmentsBean, Boolean>() {
                                    @Override
                                    public Boolean call(ChildDepartmentsBean bean) {
                                        return null != bean;
                                    }
                                });
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ChildDepartmentsBean>>() {
                    @Override
                    public void call(List<ChildDepartmentsBean> been) {
                        onSearchSicknessDepartments(been, text);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mSubscriptions.add(subscription);
    }

    private void onSearchSicknessDepartments(List<ChildDepartmentsBean> been, CharSequence text) {
        Subscription subscription = Observable.from(been)
                .filter(new Func1<ChildDepartmentsBean, Boolean>() {
                    @Override
                    public Boolean call(ChildDepartmentsBean bean) {
                        return null != bean;
                    }
                })
                .map(new Func1<ChildDepartmentsBean, List<SicknessesBean>>() {
                    @Override
                    public List<SicknessesBean> call(ChildDepartmentsBean bean) {
                        return bean.getSicknesses();
                    }
                })
                .filter(new Func1<List<SicknessesBean>, Boolean>() {
                    @Override
                    public Boolean call(List<SicknessesBean> been) {
                        return null != been;
                    }
                })
                .flatMap(new Func1<List<SicknessesBean>, Observable<SicknessesBean>>() {
                    @Override
                    public Observable<SicknessesBean> call(List<SicknessesBean> been) {
                        return Observable.from(been)
                                .filter(new Func1<SicknessesBean, Boolean>() {
                                    @Override
                                    public Boolean call(SicknessesBean bean) {
                                        return null != bean;
                                    }
                                })
                                .filter(new Func1<SicknessesBean, Boolean>() {
                                    @Override
                                    public Boolean call(SicknessesBean bean) {
                                        return bean.getSicknessName().contains(text);
                                    }
                                });
                    }
                })
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<SicknessesBean>>() {
                    @Override
                    public void call(List<SicknessesBean> been) {
                        mActivity.onSearchSicknessSucceed(been);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e(throwable.getMessage());
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
