package com.kaurihealth.datalib.response_bean;

import java.util.Date;

/**
 * Created by jianghw on 2016/8/31.
 * <p/>
 * 描述：通过患者ID查询长期监测 医生和患者的token都可以用
 */
public class LongTermMonitoringDisplayBean {

    /**
     * longTermMonitoringId : 1
     * patientId : 2
     * type : sample string 3
     * date : 2016-08-31T09:24:11+08:00
     * measurement : sample string 5
     * unit : sample string 6
     */

    private int longTermMonitoringId;
    private int patientId;
    private String type;
    private Date date;
    private String measurement;
    private String unit;

    public int getLongTermMonitoringId() {
        return longTermMonitoringId;
    }

    public void setLongTermMonitoringId(int longTermMonitoringId) {
        this.longTermMonitoringId = longTermMonitoringId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMeasurement() {
        return measurement;
    }

    public void setMeasurement(String measurement) {
        this.measurement = measurement;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
