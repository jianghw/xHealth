package com.kaurihealth.mvplib.clinical_p;

import android.widget.BaseAdapter;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.MedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewMedicalLiteratureLikeDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewMedicalLiteratureLikeDisplayBeanBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/8/29.
 */
public class DynamicActivityPresenter<V> implements IDynamicActivityPresenter<V> {

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private IDynamicActivityView mActivity;
    private boolean mFirstLoad = true;

    @Inject
    public DynamicActivityPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IDynamicActivityView) view;
    }

    @Override
    public void onSubscribe() {

        int medicalLiteratureId = mActivity.getCurrentEdicalLiteratureId();
        NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean = new NewMedicalLiteratureLikeDisplayBeanBuilder().Builder(medicalLiteratureId);
        Subscription subscription = mRepository.CheckMedicalLiteratureLike(newMedicalLiteratureLikeDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        mFragment.loadingIndicator(true);
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                    mActivity.checkIsSucess(responseDisplayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    //点赞
    public void LikeMedicalLiterature(){
        int medicalLiteratureId = mActivity.getCurrentEdicalLiteratureId();
        NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean = new NewMedicalLiteratureLikeDisplayBeanBuilder().Builder(medicalLiteratureId);
        Subscription subscription = mRepository.LikeMedicalLiterature(newMedicalLiteratureLikeDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        mFragment.loadingIndicator(true);
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MedicalLiteratureLikeDisplayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MedicalLiteratureLikeDisplayBean displayBean) {
                        mActivity.getCheckMedicalLiteratureLikeResponse(displayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void DisLikeMedicalLiterature() {

        int medicalLiteratureId = mActivity.getCurrentEdicalLiteratureId();
        NewMedicalLiteratureLikeDisplayBean newMedicalLiteratureLikeDisplayBean = new NewMedicalLiteratureLikeDisplayBeanBuilder().Builder(medicalLiteratureId);
        Subscription subscription = mRepository.DisLikeMedicalLiterature(newMedicalLiteratureLikeDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        mFragment.loadingIndicator(true);
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        mActivity.DisLikeMedicalLiterature(responseDisplayBean);
                    }
                });
        mSubscriptions.add(subscription);

    }

    @Override
    public void LoadLiteratureCommentsByMedicalLiteratureId() {
        int medicalLiteratureId = mActivity.getCurrentEdicalLiteratureId();
        Subscription subscription = mRepository.LoadLiteratureCommentsByMedicalLiteratureId(medicalLiteratureId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.dataInteractionDialog();
                        mActivity.loadingIndicator(true);
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LiteratureCommentDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mActivity.loadingIndicator(false);
//                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mActivity.loadingIndicator(false);
//                        mActivity.dismissInteractionDialog();
                    }

                    @Override
                    public void onNext(List<LiteratureCommentDisplayBean> literatureCommentDisplayBeen) {
                        mActivity.LoadLiteratureCommentsByMedicalLiteratureId(literatureCommentDisplayBeen);
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void DeleteLiteratureComment(int medicalLiteratureId, final int position){

        Subscription subscription = mRepository.DeleteLiteratureComment(medicalLiteratureId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.loadingIndicator(true);
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        mActivity.getDeleteCommentResponse(responseDisplayBean,position);
                    }
                });
        mSubscriptions.add(subscription);
    }

    //加载所有的回复信息
    public void LoadLiteratureReplyByLiteratureCommentId(int literatureCommentId, final List<LiteratureReplyDisplayBean> been, final BaseAdapter adapter){

        Subscription subscription = mRepository.LoadLiteratureReplyByLiteratureCommentId(literatureCommentId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mActivity.loadingIndicator(true);
                        mFirstLoad = false;
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<LiteratureReplyDisplayBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<LiteratureReplyDisplayBean> DisplayBean) {
//                            mActivity.getLiteratureReplyDisplayBean(DisplayBean);
                        been.clear();
                        been.addAll(DisplayBean);
                        adapter.notifyDataSetChanged();
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
