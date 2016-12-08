package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 DoctorPatientRelationshipBean 给PatientInfoActivity
 */
public class PatientInfoShipBeanEvent {

    private final DoctorPatientRelationshipBean bean;
    /**
     * 0--全部权限
     * 1--转诊权限
     */
    private final int status;

    public PatientInfoShipBeanEvent(DoctorPatientRelationshipBean bean, int status) {
        this.bean = bean;
        this.status = status;
    }

    public DoctorPatientRelationshipBean getBean() {
        return bean;
    }

    public int getPatenetStatus() {
        return status;
    }
}
