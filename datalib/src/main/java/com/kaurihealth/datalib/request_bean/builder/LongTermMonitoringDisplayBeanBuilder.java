package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;

import java.util.Date;

/**
 * Created by Nick on 2/08/2016.
 */
public class LongTermMonitoringDisplayBeanBuilder {
    public LongTermMonitoringDisplayBean Builder(int longTermMonitoringId, int patientId, String type, Date date, String measurement, String unit) {
        LongTermMonitoringDisplayBean bean = new LongTermMonitoringDisplayBean();
        bean.setUnit(unit);
        bean.setType(type);
        bean.setPatientId(patientId);
        bean.setLongTermMonitoringId(longTermMonitoringId);
        bean.setDate(date);
        bean.setMeasurement(measurement);
        return bean;
    }
}
