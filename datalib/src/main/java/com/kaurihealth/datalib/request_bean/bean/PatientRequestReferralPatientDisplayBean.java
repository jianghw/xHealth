package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KauriHealth on 2016/10/26.
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

    public static class PatientListReferralBean {
        private int patientId;
        private int doctorPatientShipId;

        public int getPatientId() {
            return patientId;
        }

        public void setPatientId(int patientId) {
            this.patientId = patientId;
        }

        public int getDoctorPatientShipId() {
            return doctorPatientShipId;
        }

        public void setDoctorPatientShipId(int doctorPatientShipId) {
            this.doctorPatientShipId = doctorPatientShipId;
        }
    }
}
