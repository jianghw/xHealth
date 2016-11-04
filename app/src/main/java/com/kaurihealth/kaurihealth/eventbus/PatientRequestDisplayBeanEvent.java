package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;

/**
 * Created by mip on 2016/9/21.
 *
 * 描述：传patientRequestDisplayBean 给PatientRequestInfoActivity
 */
public class PatientRequestDisplayBeanEvent {

    private final PatientRequestDisplayBean bean;

    public PatientRequestDisplayBeanEvent(PatientRequestDisplayBean bean){
        this.bean = bean;
    }

    public PatientRequestDisplayBean getBean(){
        return bean;
    }
}
