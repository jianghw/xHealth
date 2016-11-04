package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;

/**
 * Created by Garnet_Wu on 2016/11/1.
 */
public class DoctorInfoBeanEvent {
    private final DoctorRelationshipBean bean;

    public DoctorInfoBeanEvent(DoctorRelationshipBean bean) {
        this.bean = bean;
    }

    public DoctorRelationshipBean getBean() {
        return bean;
    }
}
