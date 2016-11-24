package com.kaurihealth.kaurihealth.home_v.referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.ConsultationReferralDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.AcceptReasonEvent;
import com.kaurihealth.kaurihealth.eventbus.HealthConditionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.LongHStandardBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.MedicalRecordIdEvent;
import com.kaurihealth.kaurihealth.eventbus.PrescriptionBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralpatientInfoEvent;
import com.kaurihealth.kaurihealth.eventbus.RejectReasonEvent;
import com.kaurihealth.kaurihealth.patient_v.health_condition.HealthConditionActivity;
import com.kaurihealth.kaurihealth.patient_v.long_monitoring.LongHealthyStandardActivity;
import com.kaurihealth.kaurihealth.patient_v.medical_records.MedicalRecordActivity;
import com.kaurihealth.kaurihealth.patient_v.prescription.PrescriptionActivity;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.dialog.DialogUtils;
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

    @Bind(R.id.ly_referral_layout)
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

    private PatientRequestDisplayBean mRequestDisplayBean;

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
        mRequestDisplayBean = event.getBean();
        setDate(mRequestDisplayBean);
    }


    private void setDate(PatientRequestDisplayBean eventBean) {
        if (eventBean == null) return;
        ConsultationReferralDisplayBean consultationReferral = eventBean.getConsultationReferral();
        if (consultationReferral != null) {
            setText(DateUtils.GetDateText(consultationReferral.getDate(), "yyyy.MM.dd HH:mm"), tv_referral_time);
            setText(consultationReferral.getComment(), tv_referral_reason);
            setText(consultationReferral.getStatus().equals("等待") ? "等待状态" : "进行时", tv_referral_info_state);
            DoctorPatientRelationshipBean relationshipBean = consultationReferral.getDoctorPatientRelationship();
            if (relationshipBean != null) {
                PatientDisplayBean patientDisplayBean = relationshipBean.getPatient();
                if (patientDisplayBean != null) {
                    ImageUrlUtils.picassoBySmallUrlCircle(this, patientDisplayBean.getAvatar(), mIvPhoto);
                    setText(patientDisplayBean.getFullName(), mTvName);
                    setText(patientDisplayBean.getGender(), mTvGendar);
                    setText(getString(R.string.patient_age, DateUtils.calculateAge(patientDisplayBean.getDateOfBirth())), mTvAge);
                    setText(getString(R.string.patient_age, DateUtils.calculateAge(patientDisplayBean.getDateOfBirth())), mTvAge);
                }
                setText(relationshipBean.getModifyRelationshipReason(), tv_referral_type);
                setText(DateUtils.GetDateText(relationshipBean.getRequestDate(), "yyyy.MM.dd HH:mm"), tv_referral_info_time);
                setText(relationshipBean.getComment(), tv_referral_info_reason);
                if (relationshipBean.getDoctor() == null) return;
                setText(relationshipBean.getDoctor().getFullName(), tv_refrral_name);
            }
        }
        textContentProcessing(eventBean.getConsultationReferral().getDoctorPatientRelationship().getComment());
    }

    /**
     * 文本处理 为请求原因添加监听
     * 描述:  文本省略号逻辑
     */
    private void textContentProcessing(final String shipBeanComment) {
        ViewTreeObserver observer = tv_referral_info_reason.getViewTreeObserver(); //textAbstract为TextView控件
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = tv_referral_info_reason.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (tv_referral_info_reason.getLineCount() > 3) {
                    int lineEndIndex = tv_referral_info_reason.getLayout().getLineEnd(2); //设置第3行打省略号
                    String text = tv_referral_info_reason.getText().subSequence(0, lineEndIndex - 3) + "...更多";
                    tv_referral_info_reason.setText(text);
                    tv_referral_info_reason.setOnClickListener(view -> DialogUtils.sweetDialog(ReferralPatientInfoActivity.this, "请求原因", shipBeanComment));
                }
            }
        });
    }

    /**
     * 点击事件
     */
    @OnClick({R.id.layHealthyRecord, R.id.lay_monitorstandard, R.id.layMedicalhistory, R.id.layRecipe, R.id.layTeam, R.id.lay_family, R.id.floatbtn_accept_new, R.id.floatbtn_refuse_new})
    public void onClickItem(View view) {
        switch (view.getId()) {
            case R.id.layHealthyRecord:
                EventBus.getDefault().postSticky(new HealthConditionBeanEvent(mRequestDisplayBean.getConsultationReferral().getDoctorPatientRelationship(), false));
                skipTo(HealthConditionActivity.class);
                break;
            case R.id.lay_monitorstandard:
                EventBus.getDefault().postSticky(new LongHStandardBeanEvent(mRequestDisplayBean.getConsultationReferral()
                        .getDoctorPatientRelationship()
                        .getPatient().getPatientId(), false, null));
                skipTo(LongHealthyStandardActivity.class);
                break;
            case R.id.layMedicalhistory:
                EventBus.getDefault().postSticky(new MedicalRecordIdEvent(mRequestDisplayBean.getConsultationReferral()
                        .getDoctorPatientRelationship()
                        .getPatient().getPatientId(),
                        mRequestDisplayBean.getConsultationReferral()
                                .getDoctorPatientRelationship(), false));
                skipTo(MedicalRecordActivity.class);
                break;
            case R.id.layRecipe:
                EventBus.getDefault().postSticky(new PrescriptionBeanEvent(mRequestDisplayBean.getConsultationReferral()
                        .getDoctorPatientRelationship(), false));
                skipTo(PrescriptionActivity.class);
                break;
            case R.id.layTeam:
                showToast(getString(R.string.patient_request_team_tips));
                break;
            case R.id.lay_family:
                showToast(getString(R.string.patient_request_familytips));
                break;
            case R.id.floatbtn_accept_new:
                EventBus.getDefault().postSticky(new AcceptReasonEvent(mRequestDisplayBean.patientRequestId));
                skipToForResult(AcceptReasonActivity.class, null, Global.RequestCode.ACCEPT);
                break;
            case R.id.floatbtn_refuse_new:
                EventBus.getDefault().postSticky(new RejectReasonEvent(mRequestDisplayBean.patientRequestId));
                skipToForResult(RejectReasonActivity.class, null, Global.RequestCode.REJECT);
                break;
        }
    }

    /**
     * 界面返回事件
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Global.RequestCode.REJECT://拒绝
                    setResult(Global.RequestCode.REJECT);
                    break;
                case Global.RequestCode.ACCEPT:
                    setResult(Global.RequestCode.ACCEPT);
                    break;
                default:
                    break;
            }
            finishCur();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
