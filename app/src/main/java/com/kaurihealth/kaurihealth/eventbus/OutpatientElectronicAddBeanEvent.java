package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by jianghw on 2016/9/12.
 * <p>
 * 描述：描述：传 DoctorPatientRelationshipBean 给  OutpatientElectronicActivity  add
 */
public class OutpatientElectronicAddBeanEvent {
    private final DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;
    private final String category;
    private final String subject;

    public OutpatientElectronicAddBeanEvent(DoctorPatientRelationshipBean shipBean, String category, String subject) {
        this.mDoctorPatientRelationshipBean = shipBean;
        this.category=category;
        this.subject=subject;
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



}
