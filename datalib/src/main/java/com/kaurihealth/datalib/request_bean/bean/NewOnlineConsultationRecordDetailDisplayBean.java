package com.kaurihealth.datalib.request_bean.bean;

/* 描述： 在线咨询实录的参数
 * Created by Nick on 21/07/2016.
 */
public class NewOnlineConsultationRecordDetailDisplayBean {
    private String onlineConsultationPurpose;   //网络咨询原因
    private String majorIssue;      //目前主要问题
    private String onlineConsultationDoctorComment;  //印象，咨询建议
    private String currentTreatment;  //目前所接受治疗
    private String presentIllness;  //主诉，现病史

    public String getOnlineConsultationPurpose() {
        return onlineConsultationPurpose;
    }

    public void setOnlineConsultationPurpose(String onlineConsultationPurpose) {
        this.onlineConsultationPurpose = onlineConsultationPurpose;
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public String getOnlineConsultationDoctorComment() {
        return onlineConsultationDoctorComment;
    }

    public void setOnlineConsultationDoctorComment(String onlineConsultationDoctorComment) {
        this.onlineConsultationDoctorComment = onlineConsultationDoctorComment;
    }

    public String getMajorIssue() {
        return majorIssue;
    }

    public void setMajorIssue(String majorIssue) {
        this.majorIssue = majorIssue;
    }

    public String getCurrentTreatment() {
        return currentTreatment;
    }

    public void setCurrentTreatment(String currentTreatment) {
        this.currentTreatment = currentTreatment;
    }
}
