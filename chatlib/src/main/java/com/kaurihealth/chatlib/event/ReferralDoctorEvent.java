package com.kaurihealth.chatlib.event;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by mip on 2016/10/25.
 */

public class ReferralDoctorEvent {

    private final DoctorPatientRelationshipBean bean;

    public ReferralDoctorEvent(DoctorPatientRelationshipBean bean) {
        this.bean = bean;
    }

    public DoctorPatientRelationshipBean getBean() {
        return bean;
    }
}
