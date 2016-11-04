package com.kaurihealth.kaurihealth.home_v.referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AcceptReasonEvent;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.PrescriptionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralpatientInfoEvent;
import com.kaurihealth.kaurihealth.eventbus.RejectReasonEvent;
import com.kaurihealth.kaurihealth.home_v.AcceptReasonActivity;
import com.kaurihealth.kaurihealth.main_v.PatientRequestActivity;
import com.kaurihealth.kaurihealth.patient_v.DoctorTeamActivity;
import com.kaurihealth.kaurihealth.patient_v.PrescriptionActivity;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthConditionActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.MedicalRecordActivity;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Created by mip on 2016/10/25.
 * <p/>
 * Describe:转诊病人信息
 */

public class ReferralPatientInfoActivity extends BaseActivity {

    @Bind(R.id.iv_photo)
    CircleImageView mIvPhoto;
    @Bind(R.id.tv_name)
    TextView mTvName;
    @Bind(R.id.tv_gendar)
    TextView mTvGendar;
    @Bind(R.id.tv_age)
    TextView mTvAge;
    @Bind(R.id.layHealthyRecord)
    LinearLayout mLayHealthyRecord;
    @Bind(R.id.lay_monitorstandard)
    LinearLayout mLayMonitorstandard;
    @Bind(R.id.layMedicalhistory)
    LinearLayout mLayMedicalhistory;
    @Bind(R.id.layRecipe)
    LinearLayout mLayRecipe;
    @Bind(R.id.layTeam)
    LinearLayout mLayTeam;
    @Bind(R.id.lay_family)
    LinearLayout mLayFamily;
    @Bind(R.id.rl_info)
    RelativeLayout rlInfo;

    @Bind(R.id.LY_referral_layout)
    LinearLayout LY_referral_layout;//转诊布局
    @Bind(R.id.tv_refrral_name)
    TextView tv_refrral_name;//转诊人
    @Bind(R.id.tv_referral_reason)
    TextView tv_referral_reason;//转诊原因
    @Bind(R.id.tv_referral_time)
    TextView tv_referral_time;//转诊时间

    @Bind(R.id.tv_referral_type)
    TextView tv_referral_type;//预约项目

    @Bind(R.id.tv_referral_info_time)
    TextView tv_referral_info_time;//预约时间
    @Bind(R.id.tv_referral_info_state)
    TextView tv_referral_info_state;//当前状态
    @Bind(R.id.tv_referral_info_reason)
    TextView tv_referral_info_reason;//预约原因

    private PatientRequestDisplayBean eventBean;
    private final int Accept = 3;
    private final int Reject = 4;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.referral_patient_info;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        initNewBackBtn("患者信息");
    }

    @Override
    protected void initDelayedData() {
        //订阅事件注册
        EventBus.getDefault().register(this);
    }

    /**
     * 订阅事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralpatientInfoEvent event) {
        eventBean = event.getBean();
        setDate(eventBean);
    }


    private void setDate(PatientRequestDisplayBean eventBean) {
        setText(eventBean.getConsultationReferral().getDoctorPatientRelationship().getDoctor().getFullName(), tv_refrral_name);
        setText(DateUtils.GetDateText(eventBean.getConsultationReferral().getDate(), "yyyy:MM:dd HH:mm"), tv_referral_time);
        setText(eventBean.getConsultationReferral().getComment(), tv_referral_reason);


        if (CheckUtils.checkUrlNotNull(eventBean.getConsultationReferral().getDoctorPatientRelationship().getPatient().getAvatar())) {
            ImageUrlUtils.picassoBySmallUrlCircle(this, eventBean.getConsultationReferral().getDoctorPatientRelationship().getPatient().getAvatar(), mIvPhoto);
        } else {
            mIvPhoto.setImageResource(R.mipmap.ic_circle_head_green);
        }

        setText(eventBean.getConsultationReferral().getDoctorPatientRelationship().getPatient().getFullName(), mTvName);
        setText(eventBean.getConsultationReferral().getDoctorPatientRelationship().getPatient().getGender(), mTvGendar);
        setText(getString(R.string.patient_age, DateUtils.calculateAge(eventBean.getConsultationReferral().getDoctorPatientRelationship().getPatient().getDateOfBirth())), mTvAge);
        setText(eventBean.getConsultationReferral().getDoctorPatientRelationship().getModifyRelationshipReason(), tv_referral_type);
        setText(DateUtils.GetDateText(eventBean.getConsultationReferral().getDoctorPatientRelationship().getRequestDate(), "yyyy:MM:dd HH:mm"), tv_referral_info_time);
        setText(eventBean.getConsultationReferral().getStatus().equals("等待") ? "等待状态" : "进行时", tv_referral_info_state);
        setText(eventBean.getConsultationReferral().getDoctorPatientRelationship().getComment(), tv_referral_info_reason);
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.layHealthyRecord, R.id.lay_monitorstandard, R.id.layMedicalhistory, R.id.layRecipe, R.id.layTeam, R.id.lay_family, R.id.floatbtn_accept_new, R.id.floatbtn_refuse_new})
    public void onClickItem(View view) {
        switch (view.getId()) {
            case R.id.layHealthyRecord:
                EventBus.getDefault().postSticky(new HealthConditionBeanEvent(eventBean.getConsultationReferral().getDoctorPatientRelationship()
                        , false));
                skipTo(HealthConditionActivity.class);
                break;
            case R.id.lay_monitorstandard:
                EventBus.getDefault().postSticky(new LongHStandardBeanEvent(eventBean.getConsultationReferral()
                        .getDoctorPatientRelationship()
                        .getPatient().getPatientId(), false, null));
                skipTo(LongHealthyStandardActivity.class);
                break;
            case R.id.layMedicalhistory:
                EventBus.getDefault().postSticky(new MedicalRecordIdEvent(eventBean.getConsultationReferral()
                        .getDoctorPatientRelationship()
                        .getPatient().getPatientId(),
                        eventBean.getConsultationReferral()
                                .getDoctorPatientRelationship(), false));
                skipTo(MedicalRecordActivity.class);

                break;
            case R.id.layRecipe:
                EventBus.getDefault().postSticky(new PrescriptionBeanEvent(eventBean.getConsultationReferral()
                        .getDoctorPatientRelationship(), false));
                skipTo(PrescriptionActivity.class);
                break;
            case R.id.layTeam:
                skipTo(DoctorTeamActivity.class);
                break;
            case R.id.lay_family:

                break;
            case R.id.floatbtn_accept_new:
                EventBus.getDefault().postSticky(new AcceptReasonEvent(eventBean.patientRequestId));
                skipToForResult(AcceptReasonActivity.class, null, Accept);
                break;
            case R.id.floatbtn_refuse_new:
                EventBus.getDefault().postSticky(new RejectReasonEvent(eventBean.patientRequestId));
                skipToForResult(RejectReasonActivity.class, null, Reject);
                break;
        }
    }


    /**
     * 界面返回事件
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Reject:
                //拒绝
                if (resultCode == RESULT_OK) {
                    setResult(PatientRequestActivity.Update);
                    finishCur();
                }
                break;
            case Accept:
                if (resultCode == RESULT_OK) {
                    setResult(PatientRequestActivity.UpdateAcc);
                    finishCur();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
