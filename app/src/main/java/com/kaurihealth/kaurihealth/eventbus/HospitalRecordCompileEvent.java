package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;

/**
 * Created by jianghw on 2016/9/21.
 * <p/>
 * Describe:
 */
public class HospitalRecordCompileEvent {
    private PatientRecordDisplayDto mPatientRecordDisplayDto;

    public HospitalRecordCompileEvent(PatientRecordDisplayDto displayDto) {
        this.mPatientRecordDisplayDto = displayDto;
    }

    public PatientRecordDisplayDto getPatientRecordDisplayDto() {
        return mPatientRecordDisplayDto;
    }
}
