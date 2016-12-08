package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralPatientDisplayBean;

import java.util.List;

/**
 * Created by KauriHealth on 2016/10/26.
 */

public class ReferralPatientReasonEvent {

    private int doctorId;

    private String str;

    private List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> referralBeen;

    public ReferralPatientReasonEvent(int doctorId,List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> referralBeen,String str){
        this.doctorId = doctorId;
        this.referralBeen = referralBeen;
        this.str = str;
    }

    public List<PatientRequestReferralPatientDisplayBean.PatientListReferralBean> getReferralBeen() {
        return referralBeen;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getStr() {
        return str;
    }
}
