package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

/**
 * Created by mip on 2016/9/28.
 */
public class AddCommonMedicalRecordBeanEvent {
    private final DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;
    private PatientRecordDisplayBean patientRecordDisplayBean;
    private String mark;


    public AddCommonMedicalRecordBeanEvent(PatientRecordDisplayBean patientRecordDisplayBean, DoctorPatientRelationshipBean shipBean,String mark) {
        this.patientRecordDisplayBean = patientRecordDisplayBean;
        this.mDoctorPatientRelationshipBean = shipBean;
        this.mark = mark;
    }

    public PatientRecordDisplayBean getPatientRecordDisplayBean() {
        return patientRecordDisplayBean;
    }

    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        return mDoctorPatientRelationshipBean;
    }

    public String getMark(){
        return mark;
    }
}
