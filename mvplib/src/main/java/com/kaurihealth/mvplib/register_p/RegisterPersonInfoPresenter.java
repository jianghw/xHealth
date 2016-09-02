package com.kaurihealth.mvplib.register_p;

import com.kaurihealth.datalib.repository.Repository;
import com.kaurihealth.datalib.request_bean.bean.DoctorUserBean;
import com.kaurihealth.datalib.request_bean.builder.DoctorUserBeanBuilder;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by mip on 2016/8/11.
 */
public class RegisterPersonInfoPresenter<V> implements IRegisterPersonInfoPresenter<V> {

    private Repository mRepository;
    private IRegisterPersonInfoView mActivity;
    private CompositeSubscription mSubscriptions;

    @Inject
    public RegisterPersonInfoPresenter(Repository repository) {
        mSubscriptions = new CompositeSubscription();
        mRepository = repository;
    }


    @Override
    public void setPresenter(V view) {
        mActivity = (IRegisterPersonInfoView) view;
    }

    @Override
    public void onSubscribe() {
        DoctorUserBean doctorUserBean = getDoctorUserBean();

        Subscription subscribe = mRepository.UpdateDoctorUserInformation(doctorUserBean)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseDisplayBean>() {
                    @Override
                    public void onCompleted() {
                        mActivity.switchPageUI(Global.Jump.MainActivity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        mActivity.showToast("保存失败,请联系管理员");
                    }

                    @Override
                    public void onNext(ResponseDisplayBean responseDisplayBean) {
                        if (responseDisplayBean.isIsSucess()) {
                            mActivity.showToast("保存成功");
                            mActivity.connectLeanCloud("");
                        }
                    }
                });
        mSubscriptions.add(subscribe);
    }

    private DoctorUserBean getDoctorUserBean() {
        String firstName = mActivity.getFirstName();
        String lastName = mActivity.getLastName();
        String gender = mActivity.getGender();
        String dayOfBirth = mActivity.getBirthday();
        return new DoctorUserBeanBuilder().Build(firstName, lastName, gender, dayOfBirth);
    }

    @Override
    public void unSubscribe() {

    }
}
