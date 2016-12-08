package com.kaurihealth.mvplib.clinical_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.LiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLiteratureReplyDisplayBean;
import com.kaurihealth.datalib.request_bean.builder.NewLiteratureReplyDisplayBeanBuilder;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/8/31.
 */
public class LiteratureCommentActivityPresenter<V> implements ILiteratureCommnetActivityPresenter<V>{

    private final IDataSource mRepository;
    private final CompositeSubscription mSubscriptions;
    private ILiteratureCommentActivityView mActivity;
    private NewLiteratureReplyDisplayBean newLiteratureReplyDisplayBean;

    @Inject
    public LiteratureCommentActivityPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }
    @Override
    public void setPresenter(V view) {
        mActivity = (ILiteratureCommentActivityView) view;
    }

    @Override
    public void onSubscribe() {

        int literatureCommentId = mActivity.getLiteratureCommentId();
        String comment = mActivity.getComment();
        newLiteratureReplyDisplayBean = new NewLiteratureReplyDisplayBeanBuilder().Builder(literatureCommentId, comment);
        Subscription subscription = mRepository.InsertLiteratureReply(newLiteratureReplyDisplayBean)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {

                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LiteratureReplyDisplayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LiteratureReplyDisplayBean literatureReplyDisplayBean) {
                            mActivity.getLiteratureReplyDisplayBean(literatureReplyDisplayBean);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
    mSubscriptions.clear();
    }
}
