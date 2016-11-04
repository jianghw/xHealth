package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by 张磊 on 2016/7/16.
 * 介绍：
 */
public class NewRemoteConsultationRecordDetailDisplayBean {
    private String presentIllness;   //主诉、现病史
    private boolean isPatientHealthRecordReviewed; //患者系统医疗记录是否已阅
    private String currentHealthIssues; //目前主要问题
    private String currentTreatment; //目前接受治疗
    private String remoteConsultationDoctorComment;  //印象，咨询建议

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public boolean isPatientHealthRecordReviewed() {
        return isPatientHealthRecordReviewed;
    }

    public void setPatientHealthRecordReviewed(boolean patientHealthRecordReviewed) {
        isPatientHealthRecordReviewed = patientHealthRecordReviewed;
    }

    public String getCurrentHealthIssues() {
        return currentHealthIssues;
    }

    public void setCurrentHealthIssues(String currentHealthIssues) {
        this.currentHealthIssues = currentHealthIssues;
    }

    public String getCurrentTreatment() {
        return currentTreatment;
    }

    public void setCurrentTreatment(String currentTreatment) {
        this.currentTreatment = currentTreatment;
    }

    public String getRemoteConsultationDoctorComment() {
        return remoteConsultationDoctorComment;
    }

    public void setRemoteConsultationDoctorComment(String remoteConsultationDoctorComment) {
        this.remoteConsultationDoctorComment = remoteConsultationDoctorComment;
    }
}
