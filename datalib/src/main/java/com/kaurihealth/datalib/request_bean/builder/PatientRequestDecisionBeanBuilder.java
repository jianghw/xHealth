package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.PatientRequestDecisionBean;

/**
 * Created by Nick on 6/07/2016.
 */
public class PatientRequestDecisionBeanBuilder {
    public PatientRequestDecisionBean Build(int patientRequestId, String reason) {
        PatientRequestDecisionBean patientRequestDecisionBean = new PatientRequestDecisionBean();
        patientRequestDecisionBean.patientRequestId = patientRequestId;
        patientRequestDecisionBean.reason = reason;
        return patientRequestDecisionBean;
    }
}
