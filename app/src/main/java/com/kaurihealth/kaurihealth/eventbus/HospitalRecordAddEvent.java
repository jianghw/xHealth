package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;

/**
 * Created by mip on 2017/1/5.
 */

public class HospitalRecordAddEvent {
    private PatientRecordDisplayDto mPatientRecordDisplayDto;
    private String mAdd;

    public HospitalRecordAddEvent(PatientRecordDisplayDto displayDto, String add) {
        this.mPatientRecordDisplayDto = displayDto;
        this.mAdd = add;
    }

    public PatientRecordDisplayDto getPatientRecordDisplayDto() {
        return mPatientRecordDisplayDto;
    }

    public String getAdd(){
        return mAdd;
    }
}
