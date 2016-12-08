package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Nick on 22/08/2016.
 */
public class SearchPresenter<V> implements ISearchPresenter<V> {
    //早mActivity
    private ISearchView mActivity;
    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;

    //Dagger
    @Inject
    SearchPresenter(IDataSource repository) {
        mRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    //IMvpPresenter 设置指挥器,拥有mActivity
    @Override
    public void setPresenter(V view) {
        mActivity = (ISearchView) view;

    }

    //IMvpPrenter 去后台Api请求数据
    @Override
    public void onSubscribe() {


    }

    //IMvpPresenter 解绑数据
    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mActivity = null;

    }

    //点击右上角的搜索按钮
    @Override
    public void clickSearchButton(String searchKeywords) {
        InitialiseSearchRequestBean initialiseSearchRequestBean = mActivity.getInitialiseSearchRequestBean();
//        ViewPager vp_content = mActivity.getViewPager();
//        int index = vp_content.getCurrentItem();
        int index = mActivity.getIndex();
        //mActivity里面的关键字集合
        String[] keyWordsList = mActivity.getKeyWordsList();
        String type = keyWordsList[index];
       // 搜索患者
        if (type.equals("patient")) {
            Subscription subscription = mRepository.SearchPatientByUserName(searchKeywords)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mActivity.dataInteractionDialog();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread()) //只可以在主线程运行
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<PatientDisplayBean>>() {
                        @Override
                        public void onCompleted() {
                            mActivity.dismissInteractionDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //查询失败的提示
                            mActivity.showToast("查询患者数据失败");

                        }

                        @Override
                        public void onNext(List<PatientDisplayBean> patientDisplayBeen) {
                            mActivity.showToast("查询患者数据成功");
                        }
                    });

            mSubscriptions.add(subscription);


        } else {
            //搜索 所有, 医生,医院, 科室
            Subscription subscription = mRepository.keyWordsSearch(initialiseSearchRequestBean)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(new Action0() {
                        @Override
                        public void call() {
                            mActivity.dataInteractionDialog();
                        }
                    })
                    .subscribeOn(AndroidSchedulers.mainThread())   //只可以在主线程运行
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<SearchResultBean>() {
                        @Override
                        public void onCompleted() {
                            mActivity.dismissInteractionDialog();
                        }

                        @Override
                        public void onError(Throwable e) {
                            //查询失败的提示
                            mActivity.showToast("查询数据失败");
                        }

                        @Override
                        public void onNext(SearchResultBean searchResultBean) {
                            mActivity.showToast("查询数据成功");
//                            if (searchResultBean.status.equals("OK")) {
//                                List<SearchDoctorDisplayBean> data = searchResultBean.result.items;
//                                ((IBlankAllView<List<SearchDoctorDisplayBean>>) fragments[selected]).setData(data);
//                                for (int i = 0; i < fragments.length; i++) {
//                                    if (i != selected) {
//                                        ((IBlankAllView<List<SearchDoctorDisplayBean>>) fragments[i]).clear();
//                                    }
//                                }
//                                mActivity.showToast("查询数据成功");
//                            }
                        }
                    });
            mSubscriptions.add(subscription);
       }
    }
}

