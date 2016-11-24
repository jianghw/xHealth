package com.kaurihealth.kaurihealth.doctor_v;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.DoctorFragmentRefreshEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorPatientRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralPatientEvent;
import com.kaurihealth.kaurihealth.home_v.request.DoctorPracticeFieldActivity;
import com.kaurihealth.kaurihealth.home_v.request.DoctorStudyExprienceActivity;
import com.kaurihealth.kaurihealth.referrals_v.ReferralPatientActivity;
import com.kaurihealth.mvplib.patient_p.DoctorInfoPresenter;
import com.kaurihealth.mvplib.patient_p.IDoctorInfoView;
import com.kaurihealth.utilslib.OnClickUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 医生信息显示页面
 */
public class DoctorInfoActivity extends BaseActivity implements IDoctorInfoView {
    @Inject
    DoctorInfoPresenter<IDoctorInfoView> mPresenter;

    @Bind(R.id.iv_photo)
    CircleImageView mIvPhoto;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_gendar)
    TextView mTvGendar;
    @Bind(R.id.tv_age)
    TextView mTvAge;

    @Bind(R.id.tv_professional)
    TextView mTvProfessional;
    @Bind(R.id.tv_major)
    TextView mTvMajor;

    @Bind(R.id.tv_practice_number)
    TextView mTvPracticeNumber;
    @Bind(R.id.tv_organization)
    TextView mTvOrganization;

    //学习与工作经历
    @Bind(R.id.lay_experience)
    LinearLayout mLayExperience;
    //专业特长
    @Bind(R.id.lay_speciality)
    LinearLayout mLaySpeciality;
    @Bind(R.id.tv_mentorshipTitle)
    TextView tv_mentorshipTitle;
    @Bind(R.id.tv_EducationTitle)
    TextView tv_EducationTitle;

    @Bind(R.id.tv_more)
    TextView mTvMore;

    @Bind(R.id.ly_visit_referral_gone)
    LinearLayout mLyVisitReferralGone;
    @Bind(R.id.floatbtn_chat)
    Button mFloatbtnChat;
    @Bind(R.id.floatbtn_referral)
    Button mFloatbtnReferral;

    private DoctorDisplayBean doctorDisplayBean;//关联医生的参数
    private int relationshipId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_doctor_info;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        initNewBackBtn(getString(R.string.doctor_tv_title));
        mTvMore.setText("删除");
        mTvMore.setTextColor(getResources().getColor(R.color.color_red));
    }

    @Override
    protected void initDelayedData() {
        EventBus.getDefault().register(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeStickyEvent(DoctorRelationshipBeanEvent.class);
        removeStickyEvent(DoctorPatientRelationshipBeanEvent.class);

        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
    }

    /**
     * --------------------------订阅---------------------------------------
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(DoctorRelationshipBeanEvent event) {
        DoctorRelationshipBean doctorRelationshipBean = event.getBean();
        relationshipId = doctorRelationshipBean.getDoctorRelationshipId();
        updateBeanData(doctorRelationshipBean);
    }

    private void updateBeanData(DoctorRelationshipBean doctorRelationshipBean) {
        doctorDisplayBean = doctorRelationshipBean.getRelatedDoctor();
        showDoctorInfo();
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)//DoctorTeamAdapter0发送事件
    public void eventBusMain(DoctorPatientRelationshipBeanEvent event) {
        //隐藏"删除"按钮-->接受DoctorTeam发来的消息
        mTvMore.setText("");
        mTvMore.setVisibility(View.INVISIBLE);
        DoctorPatientRelationshipBean doctorRelationshipBean = event.getBean();
        typeStatusHandle(event.getType());
        relationshipId = doctorRelationshipBean.getDoctorPatientId();
        doctorDisplayBean = doctorRelationshipBean.getDoctor();
        showDoctorInfo();
    }

    private void typeStatusHandle(String type) {
        mLyVisitReferralGone.setVisibility(type.equals("接受") ? View.VISIBLE : View.GONE);
        mTvMore.setVisibility(type.equals("接受") ? View.VISIBLE : View.GONE);
    }

    private void showDoctorInfo() {
        if (doctorDisplayBean != null) {
            ImageUrlUtils.picassoByUrlCircle(this, doctorDisplayBean.getAvatar(), mIvPhoto);

            setTvName(doctorDisplayBean.getFullName());
            int age = DateUtils.calculateAge(doctorDisplayBean.getDateOfBirth());
            setTvAge(getString(R.string.patient_age, age));

            setTvGendar(doctorDisplayBean.getGender());
            setTvPracticeNumber(doctorDisplayBean.getCertificationNumber());

            mTvProfessional.setText(setDefaultText(doctorDisplayBean.getHospitalTitle()));
            tv_mentorshipTitle.setText(setDefaultText(doctorDisplayBean.getMentorshipTitle()));
            tv_EducationTitle.setText(setDefaultText(doctorDisplayBean.getEducationTitle()));

            mTvMajor.setText(doctorDisplayBean.getDoctorInformations() != null
                    ? doctorDisplayBean.getDoctorInformations().getDepartment() == null ? "暂无"
                    : doctorDisplayBean.getDoctorInformations().getDepartment().getDepartmentName() : "暂无");

            DoctorInformationDisplayBean doctorInformations = doctorDisplayBean.getDoctorInformations();
            if (doctorInformations != null) {
                setTvOrganization(doctorInformations.getHospitalName());
            }
        }
    }

    @OnClick({R.id.lay_experience, R.id.lay_speciality, R.id.floatbtn_chat, R.id.tv_more, R.id.floatbtn_referral})
    public void onClick(View view) {
        if (OnClickUtils.onNoDoubleClick()) return;
        switch (view.getId()) {
            case R.id.lay_experience://学习与工作经历
                Bundle bundle_workingExperience = new Bundle();
                bundle_workingExperience.putString(Global.Bundle.REQUEST_DOCTOR_WORK, getWorkingExperience());
                skipToBundle(DoctorStudyExprienceActivity.class, bundle_workingExperience);
                break;
            case R.id.lay_speciality://特长
                Bundle bundle_practiceField = new Bundle();
                bundle_practiceField.putString(Global.Bundle.REQUEST_DOCTOR_PRACTICE, getPracticeField());
                skipToBundle(DoctorPracticeFieldActivity.class, bundle_practiceField);
                break;
            case R.id.floatbtn_chat://聊天
                try {
                    Intent intent = new Intent();
                    intent.setPackage(getApplication().getPackageName());
                    intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.putExtra(LCIMConstants.PEER_ID, doctorDisplayBean.getKauriHealthId());
                    startActivity(intent);
                } catch (ActivityNotFoundException exception) {
                    Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
                }
                break;
            case R.id.tv_more://删除
                SweetAlertDialog dialogAccept = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确定删除该协作医生?")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                mPresenter.RemoveDoctorRelationship();
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .showCancelButton(true)
                        .setCancelText("取消");
                dialogAccept.show();
                break;
            case R.id.floatbtn_referral:
                EventBus.getDefault().postSticky(new ReferralPatientEvent(doctorDisplayBean.doctorId));
                skipTo(ReferralPatientActivity.class);
                break;
            default:
                break;
        }
    }

    public String getPracticeField() {
        return doctorDisplayBean != null ? doctorDisplayBean.getPracticeField() : "暂无";
    }

    public String getWorkingExperience() {

        return doctorDisplayBean != null ? doctorDisplayBean.getWorkingExperience() : "暂无";
    }

    @Override
    public void switchPageUI(String className) {
        showToast("已删除该协作医生");
        EventBus.getDefault().postSticky(new DoctorFragmentRefreshEvent());
        finish();
    }

    public String getTvName() {
        return mTvName.getText().toString().trim();
    }

    public void setTvName(String tvName) {
        mTvName.setText(tvName);
    }

    public String getTvGendar() {
        return mTvGendar.getText().toString().trim();
    }

    public void setTvGendar(String tvGendar) {
        mTvGendar.setText(tvGendar);
    }

    public String getTvAge() {
        return mTvAge.getText().toString().trim();
    }

    public void setTvAge(String tvAge) {
        mTvAge.setText(tvAge);
    }

    public String getTvProfessional() {
        return mTvProfessional.getText().toString().trim();
    }

    public void setTvProfessional(String tvProfessional) {
        mTvProfessional.setText(tvProfessional);
    }

    public String getTvMajor() {
        return mTvMajor.getText().toString().trim();
    }

    public void setTvMajor(String tvMajor) {
        mTvMajor.setText(tvMajor);
    }

    public String getTvPracticeNumber() {
        return mTvPracticeNumber.getText().toString().trim();
    }

    public void setTvPracticeNumber(String tvPracticeNumber) {
        mTvPracticeNumber.setText(tvPracticeNumber);
    }

    public String getTvOrganization() {
        return mTvOrganization.getText().toString().trim();
    }

    public void setTvOrganization(String tvOrganization) {
        mTvOrganization.setText(tvOrganization);
    }

    @Override
    public int getRelationshipId() {
        return relationshipId;
    }
}
