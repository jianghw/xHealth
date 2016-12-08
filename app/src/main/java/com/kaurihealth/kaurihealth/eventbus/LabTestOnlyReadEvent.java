package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

/**
 * Created by mip on 2016/9/29.
 */
public class LabTestOnlyReadEvent {

    private final DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;
    private PatientRecordDisplayBean patientRecordDisplayBean;


    public LabTestOnlyReadEvent(PatientRecordDisplayBean patientRecordDisplayBean, DoctorPatientRelationshipBean shipBean) {
        this.patientRecordDisplayBean = patientRecordDisplayBean;
        this.mDoctorPatientRelationshipBean = shipBean;
    }

    public PatientRecordDisplayBean getPatientRecordDisplayBean() {
        return patientRecordDisplayBean;
    }

    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        return mDoctorPatientRelationshipBean;
    }
}
