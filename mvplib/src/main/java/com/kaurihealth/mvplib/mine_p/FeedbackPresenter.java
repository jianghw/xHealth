package com.kaurihealth.mvplib.mine_p;

import com.bugtags.library.Bugtags;
import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.utilslib.bugtag.BugTagUtils;
import com.kaurihealth.utilslib.constant.Global;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/11/28.
 * 我的--> 设置 --> 意见反馈
 */

public class FeedbackPresenter<V>  implements IFeedbackPresenter<V> {
    private IFeedbackView mActivity;
    private final IDataSource mRepository;
    private CompositeSubscription mSubscriptions;

    @Inject
    public FeedbackPresenter(IDataSource mRepository){
        this.mRepository = mRepository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void setPresenter(V view) {
        mActivity = (IFeedbackView)view;

    }

    @Override
    public void onSubscribe() {
        //将意见编辑框的内容提交到BugTag . 医生id为key, 分类是customerFeedBack  作为用户数据  ;   然后填写的意见就是sendFeedBack
        DoctorDisplayBean doctorDisplayBean = mActivity.getDoctorDisplayBean();
        String fullName = doctorDisplayBean.getFullName();
        int doctorId = doctorDisplayBean.getDoctorId();
        BugTagUtils.setUserData(Global.BugtagsConstants.BugTagUserKey,fullName);
        BugTagUtils.setUserData(Global.BugtagsConstants.BugTagDoctorId, doctorId+"");
        String customerComment = mActivity.getEditText();
        Bugtags.sendFeedback(customerComment);
        mActivity.showToast("我们已经收到您的宝贵意见~");
        mActivity.switchPageUI("");
    }

    @Override
    public void unSubscribe() {
        if (mActivity != null) mActivity.dismissInteractionDialog();
        mSubscriptions.clear();
        mActivity = null;
    }
}
