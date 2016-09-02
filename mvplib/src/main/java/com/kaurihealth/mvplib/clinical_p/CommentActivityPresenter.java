package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.LiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureCommentDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewLiteratureCommentDisplayBeanBuilder;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/8/30.
 */
public class CommentActivityPresenter<V> implements ICommentActivityPresenter<V>{

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private ICommentActivityView mActivity;
    private NewLiteratureCommentDisplayBean newLiteratureCommentDisplayBean;

    @Inject
    public CommentActivityPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (ICommentActivityView) view;
    }

    @Override
    public void onSubscribe() {

        String comment = mActivity.getComment();
        int medicalLiteratureId = mActivity.getCurrentMedicalLiteratureId();
        newLiteratureCommentDisplayBean = new NewLiteratureCommentDisplayBeanBuilder().Builder(medicalLiteratureId, comment);
        Subscription subscription = mRepository.InsertLiteratureComment(newLiteratureCommentDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        mFragment.loadingIndicator(true);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LiteratureCommentDisplayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LiteratureCommentDisplayBean literatureCommentDisplayBean) {
                        mActivity.getLiteratureCommentDisplayBean(literatureCommentDisplayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }
}
