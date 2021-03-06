package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xxx on 2016/10/26.
 * <p/>
 * Describe:
 */

public class PatientRequestReferralPatientDisplayBean implements Serializable{

    private int doctorId;
    private String requestReason;
    /**
     * patientId : 1
     * doctorPatientShipId : 2
     */

    private List<PatientListReferralBean> patientListReferral;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public List<PatientListReferralBean> getPatientListReferral() {
        return patientListReferral;
    }

    public void setPatientListReferral(List<PatientListReferralBean> patientListReferral) {
        this.patientListReferral = patientListReferral;
    }

}
