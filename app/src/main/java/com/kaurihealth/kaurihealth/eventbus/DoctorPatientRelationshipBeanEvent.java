package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 DoctorCooperationBean DoctorInfoActivity
 */
public class DoctorPatientRelationshipBeanEvent {

    private final DoctorPatientRelationshipBean bean;

    public DoctorPatientRelationshipBeanEvent(DoctorPatientRelationshipBean bean) {
        this.bean = bean;
    }

    public DoctorPatientRelationshipBean getBean() {
        return bean;
    }
}
