package com.kaurihealth.kaurihealth.home_v;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.HomeFragmentDoctorEvent;
import com.kaurihealth.mvplib.home_p.FriendRequestPresenter;
import com.kaurihealth.mvplib.home_p.IFriendRequestView;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/9/20.
 * <p/>
 * 描述：
 */
public class FriendRequestsActivity extends BaseActivity implements IFriendRequestView {
    @Inject
    FriendRequestPresenter<IFriendRequestView> mPresenter;

    @Bind(R.id.civPhoto)
    CircleImageView civPhoto;

    @Bind(R.id.tv_Friend_request_name)
    TextView tv_Friend_request_name;
    @Bind(R.id.tv_friend_request_comment)
    TextView tv_friend_request_comment;
    @Bind(R.id.tv_friend_request_professional)
    TextView tv_friend_request_professional;
    @Bind(R.id.tv_friend_request_EducationTitle)
    TextView tv_friend_request_EducationTitle;
    @Bind(R.id.RL_friend_request_details)
    RelativeLayout RL_friend_request_details;
    @Bind(R.id.RL_friend_request_agree_and_reject)
    RelativeLayout RL_friend_request_agree_and_reject;
    @Bind(R.id.tv_friend_request_tips)
    TextView tv_friend_request_tips;
    @Bind(R.id.tv_friend_request_mentorshipTitle)
    TextView tv_friend_request_mentorshipTitle;

    private int DOCTOR_INFO = 11;
    private DoctorRelationshipBean doctorRelationshipBean;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.friend_request;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("好友申请");

        Bundle bundle = getBundle();
        if (bundle != null) {
            doctorRelationshipBean = (DoctorRelationshipBean) bundle.getSerializable("doctorRelationshipBean");
        }
        if (doctorRelationshipBean == null) return;
        judgeStatus();
        if (doctorRelationshipBean.getRelatedDoctor() != null) {
            if (CheckUtils.checkUrlNotNull(doctorRelationshipBean.getRelatedDoctor().getAvatar())) {
                ImageUrlUtils.picassoBySmallUrlCircle(this, doctorRelationshipBean.getRelatedDoctor().getAvatar(), civPhoto);
            } else {
                civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
            }

            tv_friend_request_professional.setText(doctorRelationshipBean.getRelatedDoctor().getHospitalTitle());
            tv_friend_request_EducationTitle.setText(doctorRelationshipBean.getRelatedDoctor().getEducationTitle());
            tv_friend_request_mentorshipTitle.setText(doctorRelationshipBean.getRelatedDoctor().getMentorshipTitle());
            tv_Friend_request_name.setText(doctorRelationshipBean.getRelatedDoctor().getFullName());
        }
        tv_friend_request_comment.setText(doctorRelationshipBean.getComment());
    }

    /**
     * 判断其状态
     */
    private void judgeStatus() {
        if (doctorRelationshipBean.status.equals("等待")) {
            tv_friend_request_tips.setVisibility(View.GONE);
        } else if (doctorRelationshipBean.status.equals("接受")) {
            RL_friend_request_agree_and_reject.setVisibility(View.GONE);
            tv_friend_request_tips.setText("已同意该申请");
        } else {
            RL_friend_request_agree_and_reject.setVisibility(View.GONE);
            tv_friend_request_tips.setText("已拒绝该申请");
        }
    }

    @Override
    public void switchPageUI(String className) {

    }


    @OnClick({R.id.tv_friend_request_agree, R.id.tv_friend_request_reject, R.id.RL_friend_request_details})
    public void onClickToAction(View view) {
        switch (view.getId()) {
            case R.id.tv_friend_request_agree:
                mPresenter.onSubscribe();//同意
                break;
            case R.id.tv_friend_request_reject:
                mPresenter.RejectDoctorRelationship();//绝决
                break;
            case R.id.RL_friend_request_details:
                Bundle bundle = new Bundle();
                bundle.putSerializable("CurrentDoctorRelationshipBean", doctorRelationshipBean);
                skipToBundle(DoctorRequestInfoActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doctorRelationshipBean = null;
        mPresenter.unSubscribe();
    }


    /**
     * 得到当前医生关系的id
     *
     * @return
     */
    @Override
    public int getCurrentDoctorRelationshipId() {
        return doctorRelationshipBean != null ? doctorRelationshipBean.getDoctorRelationshipId() : 0;
    }

    //同意好友响应
    @Override
    public void agreeFriendSuccess(ResponseDisplayBean bean) {
        RL_friend_request_agree_and_reject.setVisibility(View.GONE);
        tv_friend_request_tips.setVisibility(View.VISIBLE);
        tv_friend_request_tips.setText("已同意该申请");
        EventBus.getDefault().postSticky(new HomeFragmentDoctorEvent("Accept"));
        setResult(200);
        finishCur();
    }

    //拒绝好友响应
    @Override
    public void rejectFriendSuccess(ResponseDisplayBean bean) {
        RL_friend_request_agree_and_reject.setVisibility(View.GONE);
        tv_friend_request_tips.setVisibility(View.VISIBLE);
        tv_friend_request_tips.setText("已拒绝该申请");
        EventBus.getDefault().postSticky(new HomeFragmentDoctorEvent("Reject"));
        setResult(400);
        finishCur();
    }
}

