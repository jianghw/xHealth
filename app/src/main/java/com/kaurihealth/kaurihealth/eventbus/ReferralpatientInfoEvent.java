package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;

/**
 * Created by mip on 2016/10/25.
 */

public class ReferralpatientInfoEvent {

    private PatientRequestDisplayBean bean;

    public ReferralpatientInfoEvent(PatientRequestDisplayBean bean){
        this.bean = bean;
    }

    public PatientRequestDisplayBean getBean() {
        return bean;
    }
}
