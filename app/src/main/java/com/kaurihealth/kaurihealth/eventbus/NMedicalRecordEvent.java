package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by mip on 2017/1/4.
 */

public class NMedicalRecordEvent {

    private int mPatientId;

    public NMedicalRecordEvent(int patientId){
        this.mPatientId = patientId;
    }

    public int  getmPatientId(){
        return mPatientId;
    }
}
