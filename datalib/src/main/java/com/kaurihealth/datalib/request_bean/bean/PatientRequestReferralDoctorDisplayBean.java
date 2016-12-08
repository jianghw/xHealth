package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mip on 2016/10/25.
 *  向多个医生转诊多个病人
 */

public class PatientRequestReferralDoctorDisplayBean implements Serializable{

    /**
     * patientId : 1
     * requestReason : sample string 2
     * doctorPatientRelationshipId : 3
     */

    private DoctorListReferralBean doctorListReferral;
    /**
     * doctorListReferral : {"patientId":1,"requestReason":"sample string 2","doctorPatientRelationshipId":3}
     * doctorListId : [1,2]
     */

    private List<Integer> doctorListId;

    public DoctorListReferralBean getDoctorListReferral() {
        return doctorListReferral;
    }

    public void setDoctorListReferral(DoctorListReferralBean doctorListReferral) {
        this.doctorListReferral = doctorListReferral;
    }

    public List<Integer> getDoctorListId() {
        return doctorListId;
    }

    public void setDoctorListId(List<Integer> doctorListId) {
        this.doctorListId = doctorListId;
    }

    public static class DoctorListReferralBean {
        private int patientId;
        private String requestReason;
        private int doctorPatientRelationshipId;

        public int getPatientId() {
            return patientId;
        }

        public void setPatientId(int patientId) {
            this.patientId = patientId;
        }

        public String getRequestReason() {
            return requestReason;
        }

        public void setRequestReason(String requestReason) {
            this.requestReason = requestReason;
        }

        public int getDoctorPatientRelationshipId() {
            return doctorPatientRelationshipId;
        }

        public void setDoctorPatientRelationshipId(int doctorPatientRelationshipId) {
            this.doctorPatientRelationshipId = doctorPatientRelationshipId;
        }
    }
}
