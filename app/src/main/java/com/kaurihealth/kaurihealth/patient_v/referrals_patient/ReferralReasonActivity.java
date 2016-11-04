package com.kaurihealth.kaurihealth.patient_v.referrals_patient;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralDoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralPatientDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.eventbus.ReferralPatientReasonEvent;
import com.kaurihealth.kaurihealth.eventbus.ReferralReasonEvent;
import com.kaurihealth.mvplib.patient_p.IReferralReasonView;
import com.kaurihealth.mvplib.patient_p.ReferralReasonPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by mip on 2016/10/25.
 */

public class ReferralReasonActivity extends BaseActivity implements IReferralReasonView {
    @Bind(R.id.tv_more)
    TextView tv_more;
    @Bind(R.id.edt_referral_reason)
    EditText edt_referral_reason;

    @Inject
    ReferralReasonPresenter<IReferralReasonView> mPresenter;
    private int doctorPatientRelationshipId;
    private List<Integer> doctorIdlist;
    private int patientId;
    private int doctorId;
    private String mark = null;
    private List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> referralBeen;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.referral_reason;
    }


    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initDelayedData() {
        initNewBackBtn("转诊原因");
        tv_more.setText("确定");
    }

    /**
     * 订阅事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralReasonEvent event) {
        patientId = event.getPatientId();
        doctorIdlist = event.getDoctorIdlist();
        doctorPatientRelationshipId = event.getDoctorPatientRelationshipId();
    }

    /**
     * 订阅事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(ReferralPatientReasonEvent event) {
        doctorId = event.getDoctorId();
        referralBeen = event.getReferralBeen();
        mark = event.getStr();

    }


    @OnClick(R.id.tv_more)
    public void onConfirm() {
        if (mark == null) {
            mPresenter.onSubscribe();
        }else {
            mPresenter.InsertPatientRequestReferralByPatientList();
        }

    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //有多个事件注意 移除
        removeStickyEvent(ReferralPatientReasonEvent.class);
        removeStickyEvent(ReferralReasonEvent.class);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void getresult(ResponseDisplayBean displayBean) {
        if (displayBean.isIsSucess()) {
            showToast("转诊成功");
            setResult(RESULT_OK);
            finishCur();
        } else {
            showToast(displayBean.getMessage());
        }
    }

    /**
     * 得到bean
     *
     * @return
     */
    @Override
    public PatientRequestReferralDoctorDisplayBean getBean() {
        PatientRequestReferralDoctorDisplayBean referralDoctorDisplayBean = new PatientRequestReferralDoctorDisplayBean();
        PatientRequestReferralDoctorDisplayBean.DoctorListReferralBean bean =
                new PatientRequestReferralDoctorDisplayBean.DoctorListReferralBean();
        bean.setDoctorPatientRelationshipId(doctorPatientRelationshipId);
        bean.setPatientId(patientId);
        bean.setRequestReason(edt_referral_reason.getText().toString().trim());
        referralDoctorDisplayBean.setDoctorListId(doctorIdlist);
        referralDoctorDisplayBean.setDoctorListReferral(bean);
        return referralDoctorDisplayBean;
    }

    /**
     * 得到向医生转诊多个患者的bean
     * @return
     */
    @Override
    public PatientRequestReferralPatientDisplayBean getPatientRequestBean() {
        PatientRequestReferralPatientDisplayBean displayBean = new PatientRequestReferralPatientDisplayBean();
        displayBean.setDoctorId(doctorId);
        displayBean.setRequestReason(edt_referral_reason.getText().toString().trim());
        displayBean.setPatientListReferral(referralBeen);

        return displayBean;
    }
}
