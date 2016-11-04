package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 DoctorCooperationBean DoctorInfoActivity
 */
public class DoctorRelationshipBeanEvent {

    private final DoctorRelationshipBean bean;

    public DoctorRelationshipBeanEvent(DoctorRelationshipBean bean) {
        this.bean = bean;
    }

    public DoctorRelationshipBean getBean() {
        return bean;
    }
}
