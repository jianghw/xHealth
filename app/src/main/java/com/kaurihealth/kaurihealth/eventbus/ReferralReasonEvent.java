package com.kaurihealth.kaurihealth.eventbus;

import java.util.List;

/**
 * Created by mip on 2016/10/25.
 */

public class ReferralReasonEvent {

    private int patientId;
    private int doctorPatientRelationshipId;
    private List<Integer> doctorIdlist;

    public ReferralReasonEvent(int patientId,int doctorPatientRelationshipId,List<Integer> doctorIdlist){
        this.patientId = patientId;
        this.doctorPatientRelationshipId = doctorPatientRelationshipId;
        this.doctorIdlist = doctorIdlist;
    }
    public int getPatientId() {
        return patientId;
    }

    public int getDoctorPatientRelationshipId() {
        return doctorPatientRelationshipId;
    }

    public List<Integer> getDoctorIdlist() {
        return doctorIdlist;
    }
}
