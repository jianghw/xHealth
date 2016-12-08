package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/9/8.
 * <p>
 * 描述：
 */
public class OnlineConsultationRecordBean {   //OnlineConsultationRecordDetailDisplayDto
    private int onlineConsultationRecordId;  //网络医疗咨询病历ID
    private String onlineConsultationPurpose;  //网络咨询原因
    private String majorIssue;     //目前主要问题
    private String onlineConsultationDoctorComment;  //印象/咨询建议
    private int recordId;     //病例ID
    private String currentTreatment; //目前所接受治疗
    private String presentIllness;  //主诉/现病史

    public int getOnlineConsultationRecordId() {
        return onlineConsultationRecordId;
    }

    public void setOnlineConsultationRecordId(int onlineConsultationRecordId) {
        this.onlineConsultationRecordId = onlineConsultationRecordId;
    }

    public String getOnlineConsultationPurpose() {
        return onlineConsultationPurpose;
    }

    public void setOnlineConsultationPurpose(String onlineConsultationPurpose) {
        this.onlineConsultationPurpose = onlineConsultationPurpose;
    }

    public String getMajorIssue() {
        return majorIssue;
    }

    public void setMajorIssue(String majorIssue) {
        this.majorIssue = majorIssue;
    }

    public String getOnlineConsultationDoctorComment() {
        return onlineConsultationDoctorComment;
    }

    public void setOnlineConsultationDoctorComment(String onlineConsultationDoctorComment) {
        this.onlineConsultationDoctorComment = onlineConsultationDoctorComment;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getCurrentTreatment() {
        return currentTreatment;
    }

    public void setCurrentTreatment(String currentTreatment) {
        this.currentTreatment = currentTreatment;
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

}
