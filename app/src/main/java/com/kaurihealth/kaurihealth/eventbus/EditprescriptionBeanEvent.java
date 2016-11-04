package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;

/**
 * Created by mip on 2016/9/9.
 */
public class EditprescriptionBeanEvent {


    private final PrescriptionBean bean;
    private final String kauriHealthId;
    private final int patientId;

    public EditprescriptionBeanEvent(PrescriptionBean bean,String kauriHealthId,int patientId) {
        this.bean = bean;
        this.kauriHealthId = kauriHealthId;
        this.patientId = patientId;
    }

    public PrescriptionBean getBean() {
        return bean;
    }
    public String getKauriHealthId(){
        return kauriHealthId;
    }
    public int getPatientId(){
        return patientId;
    }
}
