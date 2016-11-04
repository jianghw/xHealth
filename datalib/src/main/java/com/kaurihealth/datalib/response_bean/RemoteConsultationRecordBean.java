package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/9/8.
 * <p>
 * 描述：
 */
public class RemoteConsultationRecordBean {   //RemoteConsultationRecordDetailDisplayDto
    private int remoteConsultationRecordId; //远程医疗咨询病历ID
    private String presentIllness;    //主诉/现病史
    private boolean isPatientHealthRecordReviewed;   //患者系统医疗记录是否已阅
    private String currentHealthIssues;  // 目前主要问题
    private String currentTreatment;   //目前所接受治疗
    private String remoteConsultationDoctorComment;    //印象/咨询建议
    private int recordId;   // 	  病例ID

    public int getRemoteConsultationRecordId() {
        return remoteConsultationRecordId;
    }

    public void setRemoteConsultationRecordId(int remoteConsultationRecordId) {
        this.remoteConsultationRecordId = remoteConsultationRecordId;
    }

    public String getPresentIllness() {
        return presentIllness;
    }

    public void setPresentIllness(String presentIllness) {
        this.presentIllness = presentIllness;
    }

    public boolean isIsPatientHealthRecordReviewed() {
        return isPatientHealthRecordReviewed;
    }

    public void setIsPatientHealthRecordReviewed(boolean isPatientHealthRecordReviewed) {
        this.isPatientHealthRecordReviewed = isPatientHealthRecordReviewed;
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

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

}
