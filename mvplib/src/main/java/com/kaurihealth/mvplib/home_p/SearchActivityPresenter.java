package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.request_bean.builder.SearchBooleanResultBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.SearchErrorsBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;

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
 * Created by jianghw on 2016/9/18.
 * <p/>
 * Describe:查询页面
 */
public class SearchActivityPresenter<V> implements ISearchActivityPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ISearchActivityView mActivity;

    @Inject
    SearchActivityPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (ISearchActivityView) view;
    }

    /**
     * 默认查询
     * 1、有错误时
     * 2、查询到数据时
     * 3、查询不到数据时
     */
    @Override
    public void onSubscribe() {
        InitialiseSearchRequestBean currentSearchRequestBean = mActivity.getCurrentSearchRequestBean();
        Subscription subscription = mRepository.KeywordSearch(currentSearchRequestBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(() -> mActivity.dataInteractionDialog())  //正在加载中...
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<SearchResultBean>() {
                            @Override
                            public void call(SearchResultBean searchResultBean) {
                                if (searchResultBean.getErrors() != null && searchResultBean.getErrors().size() > 0) {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (SearchErrorsBean errorsBean : searchResultBean.getErrors()) {
                                        stringBuilder.append(errorsBean.getMessage());
                                    }
                                    mActivity.displayErrorDialog(stringBuilder.toString());
                                } else if (searchResultBean.getResult() != null && searchResultBean.getResult().getNum() > 0) {
                                    loadDoctorRelationships(searchResultBean.getResult().getItems());
                                } else {
                                    mActivity.updataDataSucceed(null);
                                    mActivity.dismissInteractionDialog();
                                }
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mActivity.displayErrorDialog(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }


    /**
     * 查询用户医生关系的病例（不包含拒绝的） 医生使用的token
     */
    public void loadDoctorRelationships(List<SearchResultBean.ResultBean.ItemsBean> items) {
        Subscription subscription = mRepository.LoadDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<DoctorRelationshipBean>>() {
                            @Override
                            public void call(List<DoctorRelationshipBean> doctorRelationshipBeen) {
                                handleDoctorRelationship(doctorRelationshipBeen, items);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mActivity.displayErrorDialog(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 1、取医生id
     * 2、转list --关系列表id集合
     * 3、重发ItemsBean
     * 4、转SearchBooleanResultBean
     */
    public void handleDoctorRelationship(List<DoctorRelationshipBean> been, List<SearchResultBean.ResultBean.ItemsBean> items) {
        Subscription subscription = Observable.from(been)
                .subscribeOn(Schedulers.computation())
                .map(new Func1<DoctorRelationshipBean, Integer>() {
                    @Override
                    public Integer call(DoctorRelationshipBean bean) {
                        return bean.getRelatedDoctor().getDoctorId();
                    }
                })
                .toList()
                .flatMap(new Func1<List<Integer>, Observable<SearchBooleanResultBean>>() {
                    @Override
                    public Observable<SearchBooleanResultBean> call(List<Integer> integers) {
                        return Observable
                                .from(items)
                                .map(new Func1<SearchResultBean.ResultBean.ItemsBean, SearchBooleanResultBean>() {
                                    @Override
                                    public SearchBooleanResultBean call(SearchResultBean.ResultBean.ItemsBean bean) {
                                        return new SearchBooleanResultBeanBuilder().Build(integers.contains(Integer.valueOf(bean.getDoctorId())), bean);
                                    }
                                });
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchBooleanResultBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mActivity.displayErrorDialog(throwable.getMessage());
                    }

                    @Override
                    public void onNext(List<SearchBooleanResultBean> been) {
                        mActivity.updataDataSucceed(been);
                    }
                });
        mSubscriptions.add(subscription);
    }

    /**
     * 询医生信息
     */
    public void loadDoctorDetail() {
        Subscription subscription = mRepository.loadDoctorDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DoctorDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.displayErrorDialog(e.getMessage());
                        mActivity.showToast("数据加载出错,请退出当前页面");
                    }

                    @Override
                    public void onNext(DoctorDisplayBean doctorDisplayBean) {
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
