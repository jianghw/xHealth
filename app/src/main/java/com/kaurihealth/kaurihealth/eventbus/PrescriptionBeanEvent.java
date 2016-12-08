package com.kaurihealth.kaurihealth.eventbus;


import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;

/**
 * Created by mip on 2016/9/7.
 * 描述：给Prescription传递DactorPatientRelationshipBean
 */
public class PrescriptionBeanEvent {

    private final DoctorPatientRelationshipBean bean;
    private final boolean isAble;

    public PrescriptionBeanEvent(DoctorPatientRelationshipBean bean,boolean isAble) {
        this.bean = bean;
        this.isAble =isAble;
    }

    public DoctorPatientRelationshipBean getBean() {
        return bean;
    }
    public boolean getIsAble(){
        return isAble;
    }
}
