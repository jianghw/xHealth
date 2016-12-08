package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.NewLongtermMonitoringBean;

import java.util.Date;

/**
 * Created by jianghw on 2016/8/31.
 * <p/>
 * 描述：
 */
public class NewLongtermMonitoringBeanBuilder {
    public NewLongtermMonitoringBean Builder(int patientId, String type, Date date, String measurement, String unit) {
        NewLongtermMonitoringBean bean = new NewLongtermMonitoringBean();
        bean.setPatientId(patientId);
        bean.setType(type);
        bean.setDate(date);
        bean.setMeasurement(measurement);
        bean.setUnit(unit);
        return bean;
    }
}
