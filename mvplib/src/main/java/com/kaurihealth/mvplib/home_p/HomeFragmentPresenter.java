package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jianghw on 2016/9/19.
 * <p>
 * 描述：
 */
public class HomeFragmentPresenter<V> implements IHomeFragmentPresenter<V> {
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;
    private IHomeFragmentView mFragment;

    private boolean mFirstLoad_Ships = true;
    private boolean mFirstLoad_Number = true;

    @Inject
    public HomeFragmentPresenter(IDataSource mRepository) {
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mFragment = (IHomeFragmentView) view;
    }

    @Override
    public void onSubscribe() {
        if (mFirstLoad_Ships) loadPendingDoctorRelationships(false);
    }

    /**
     * 查询挂起的医生关系
     */
    @Override
    public void loadPendingDoctorRelationships(boolean isDirty) {
        Subscription subscription = mRepository.LoadPendingDoctorRelationships()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<DoctorRelationshipBean>>() {
                    @Override
                    public void onCompleted() {
                        mFirstLoad_Ships = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<DoctorRelationshipBean> doctorRelationshipBean) {
                        int count = 0;
                        for (DoctorRelationshipBean bean : doctorRelationshipBean) {
                            if (bean != null && bean.getStatus().equals("等待")) {
                                count = count + 1;
                            }
                        }
                        mFragment.getDoctorRequestNumber(count);
                    }
                });
        mSubscriptions.add(subscription);
    }

    @Override
    public void getPatientRequestNumber() {
        if (mFirstLoad_Number) loadPatientRequestsByDoctor(false);
    }

    /**
     * 通过医生查询候诊患者的请求
     */
    @Override
    public void loadPatientRequestsByDoctor(boolean b) {
        Subscription subscription = mRepository.LoadPatientRequestsByDoctor()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRequestDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mFirstLoad_Number = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PatientRequestDisplayBean> patientRequestDisplayBeen) {
                        mFragment.getPatientRequestNumber(patientRequestDisplayBeen.size());
                    }
                });
        mSubscriptions.add(subscription);
    }

    public void loadReferralPatientRequests(boolean b) {
        Subscription subscription = mRepository.LoadReferralsPatientRequestByDoctorId()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PatientRequestDisplayBean>>() {
                    @Override
                    public void onCompleted() {
                        mFirstLoad_Number = false;
                    }

                    @Override
                    public void onError(Throwable e) {
                        mFragment.showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(List<PatientRequestDisplayBean> patientRequestDisplayBeen) {
                        mFragment.getReferralPatientRequestNumber(patientRequestDisplayBeen.size());
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
