package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanPatientBean;
import com.kaurihealth.datalib.request_bean.builder.SearchBooleanPatientBeanBuilder;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

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
 * Created by jianghw on 2016/9/19.
 * <p/>
 * Describe:
 */
public class SearchPatientFragmentPresenter<V> implements IMvpPresenter<V> {

    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private ISearchpatientFragmentView mFragment;

    @Inject
    public SearchPatientFragmentPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (ISearchpatientFragmentView) view;
    }

    /**
     * 搜索
     */
    @Override
    public void onSubscribe() {
        String content = mFragment.getEditTextContent();
        if (content == null || content != null && content.length() < 1) {
            mFragment.processingData(null);
        } else {
            Subscription subscription = mRepository.SearchPatientByUserName(content)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(() -> mFragment.dataInteractionDialog())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Action1<List<PatientDisplayBean>>() {
                                @Override
                                public void call(List<PatientDisplayBean> been) {
                                    loadDoctorPatientRelationshipForDoctor(been);
                                }
                            },
                            new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    mFragment.displayErrorDialog(throwable.getMessage());
                                }
                            });
            mSubscriptions.add(subscription);
        }
    }

    /**
     * 医患关系
     */
    public void loadDoctorPatientRelationshipForDoctor(List<PatientDisplayBean> patientDisplayBeen) {
        Subscription subscription = mRepository.LoadDoctorPatientRelationshipForDoctor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<List<DoctorPatientRelationshipBean>>() {
                            @Override
                            public void call(List<DoctorPatientRelationshipBean> been) {
                                handlePatientRelationship(been, patientDisplayBeen);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                mFragment.displayErrorDialog(throwable.getMessage());
                            }
                        });
        mSubscriptions.add(subscription);
    }

    /**
     * 数据处理
     */
    private void handlePatientRelationship(List<DoctorPatientRelationshipBean> relationshipBeen, List<PatientDisplayBean> patientDisplayBeen) {
        Subscription subscription = Observable.from(relationshipBeen)
                .map(new Func1<DoctorPatientRelationshipBean, Integer>() {
                    @Override
                    public Integer call(DoctorPatientRelationshipBean bean) {
                        return bean.getPatientId();
                    }
                })
                .toList()
                .flatMap(new Func1<List<Integer>, Observable<SearchBooleanPatientBean>>() {
                    @Override
                    public Observable<SearchBooleanPatientBean> call(List<Integer> integers) {
                        return Observable.from(patientDisplayBeen)
                                .map(new Func1<PatientDisplayBean, SearchBooleanPatientBean>() {
                                    @Override
                                    public SearchBooleanPatientBean call(PatientDisplayBean bean) {
                                        return new SearchBooleanPatientBeanBuilder().Build(integers.contains(bean.getPatientId()), bean);
                                    }
                                });
                    }
                })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SearchBooleanPatientBean>>() {
                    @Override
                    public void onCompleted() {
                        mFragment.dismissInteractionDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.displayErrorDialog(e.getMessage());
                    }

                    @Override
                    public void onNext(List<SearchBooleanPatientBean> been) {
                        mFragment.processingData(been);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
        mFragment = null;
    }
}
