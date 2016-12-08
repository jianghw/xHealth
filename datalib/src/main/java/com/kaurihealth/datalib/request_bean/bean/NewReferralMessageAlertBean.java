package com.kaurihealth.datalib.request_bean.bean;

import java.util.Date;

/**
 * Created by jianghw on 2016/11/9.
 * <p/>
 * Describe:插入新的复诊提醒信息
 */

public class NewReferralMessageAlertBean {

    /**
     * reminderTime : 2016-11-09T13:48:41+08:00
     * reminderContent : sample string 2
     * doctorPatientShipId : 3
     * creatTime : 2016-11-09T13:48:41+08:00
     */

    private Date reminderTime;
    private String reminderContent;
    private int doctorPatientShipId;
    private Date creatTime;

    public Date getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(Date reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getReminderContent() {
        return reminderContent;
    }

    public void setReminderContent(String reminderContent) {
        this.reminderContent = reminderContent;
    }

    public int getDoctorPatientShipId() {
        return doctorPatientShipId;
    }

    public void setDoctorPatientShipId(int doctorPatientShipId) {
        this.doctorPatientShipId = doctorPatientShipId;
    }

    public Date getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
}
