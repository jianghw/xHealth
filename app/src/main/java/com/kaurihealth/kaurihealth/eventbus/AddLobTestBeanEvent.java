package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by mip on 2016/9/29.
 */
public class AddLobTestBeanEvent {

    private final DoctorPatientRelationshipBean mDoctorPatientRelationshipBean;
    private final String category;
    private final String subject;

    public AddLobTestBeanEvent(DoctorPatientRelationshipBean shipBean, String category, String subject) {
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
