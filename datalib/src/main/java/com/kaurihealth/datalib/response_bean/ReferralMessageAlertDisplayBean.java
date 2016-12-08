package com.kaurihealth.datalib.response_bean;

import java.util.Date;

/**
 * Created by jianghw on 2016/11/9.
 * <p/>
 * Describe:根据医患关系ID加载复诊提醒信息
 */

public class ReferralMessageAlertDisplayBean {

    /**
     * referralMessageAlertId : 1
     * reminderTime : 2016-11-09T13:49:50+08:00
     * reminderContent : sample string 3
     * isSend : true
     * doctorPatientShipId : 5
     * creatTime : 2016-11-09T13:49:50+08:00
     */

    private int referralMessageAlertId;
    private Date reminderTime;
    private String reminderContent;
    private boolean isSend;
    private int doctorPatientShipId;
    private Date creatTime;

    public int getReferralMessageAlertId() {
        return referralMessageAlertId;
    }

    public void setReferralMessageAlertId(int referralMessageAlertId) {
        this.referralMessageAlertId = referralMessageAlertId;
    }

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

    public boolean isIsSend() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend = isSend;
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
