package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by Garnet_Wu on 2016/11/18.
 */
public class FamilyMemberEvent {
    private  int patientId;
    public FamilyMemberEvent(int  patientId){
        this.patientId = patientId;
    }

    public int  getPatientId(){
        return  patientId;
    }
}
