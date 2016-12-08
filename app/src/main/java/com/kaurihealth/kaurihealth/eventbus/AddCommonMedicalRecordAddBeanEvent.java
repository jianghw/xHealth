package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by mip on 2016/9/27.
 */
public class AddCommonMedicalRecordAddBeanEvent {
    private final DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;
    private final String category;
    private final String subject;
    private final String mark;

    public AddCommonMedicalRecordAddBeanEvent(DoctorPatientRelationshipBean shipBean, String category, String subject,String mark) {
        this.mDoctorPatientRelationshipBean = shipBean;
        this.category=category;
        this.subject=subject;
        this.mark = mark;
    }

    public DoctorPatientRelationshipBean getDoctorPatientRelationshipBean() {
        return mDoctorPatientRelationshipBean;
    }

    public String getCategory() {
        return category;
    }

    public String getSubject() {
        return subject;
    }

    public String getMark(){
        return mark;
    }
}
