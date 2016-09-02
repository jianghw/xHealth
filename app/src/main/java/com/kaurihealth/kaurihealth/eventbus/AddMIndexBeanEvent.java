package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 DoctorPatientRelationshipBean 给  AddMonitorIndexActivity
 */
public class AddMIndexBeanEvent {

    private final DoctorPatientRelationshipBean bean;

    public AddMIndexBeanEvent(DoctorPatientRelationshipBean bean) {
        this.bean = bean;
    }

    public DoctorPatientRelationshipBean getBean() {
        return bean;
    }
}
