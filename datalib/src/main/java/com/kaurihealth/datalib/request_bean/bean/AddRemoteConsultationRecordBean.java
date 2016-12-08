package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class AddRemoteConsultationRecordBean {
    //默认
    public String status;
    //需要设置
    public int patientId;
    public String comment;
    public String recordDate;
    public String doctor;
    public String hospital;
    public int departmentId;
    public String subject;
    public RecordDisplayDto record = new RecordDisplayDto();
    public DepartmentDisplayDto department = new DepartmentDisplayDto();
    public List<NewDocumentDisplayBean> recorddocuments;

    /**
     * @param patientId             患者ID
     * @param comment               备注
     * @param recordDate            门诊时间
     * @param doctor                医师
     * @param hospital              机构
     * @param departmentId          科室ID
     * @param subject               项目
     * @param departmentName        科室名称
     * @param presentIllness        主诉/现病史
     * @param isPastIllnessReviewed 既往病史    既往病史已阅
     * @param treatmentAndPlan      印象/咨询建议
     * @param currentTreatment      目前所接收治疗
     * @param currentHealthIssues   目前主要问题
     */
    public AddRemoteConsultationRecordBean(int patientId, String comment,
                                           String doctor, String recordDate,
                                           String hospital, int departmentId,
                                           String subject, String departmentName,
                                           String presentIllness, boolean isPastIllnessReviewed,
                                           String treatmentAndPlan, String currentHealthIssues,
                                           String currentTreatment,
                                           List<NewDocumentDisplayBean> recorddocuments, Status status) {
        this.patientId = patientId;
        this.comment = comment;
        this.doctor = doctor;
        this.recordDate = recordDate;
        this.hospital = hospital;
        this.departmentId = departmentId;
        this.subject = subject;
        this.department.departmentId = departmentId;
        this.record.recordType = subject;
        this.record.ownerId = patientId;
        this.department.departmentName = departmentName;
        this.record.remoteConsultationRecord.presentIllness = presentIllness;
        this.record.remoteConsultationRecord.isPatientHealthRecordReviewed = isPastIllnessReviewed;
        this.record.remoteConsultationRecord.currentHealthIssues = currentHealthIssues;
        this.record.remoteConsultationRecord.currentTreatment = currentTreatment;
        this.record.remoteConsultationRecord.remoteConsultationDoctorComment = treatmentAndPlan;
        this.recorddocuments = recorddocuments;
        this.status = status.value;
    }

    public List<NewDocumentDisplayBean> getRecorddocuments() {
        return recorddocuments;
    }

    public void setRecorddocuments(List<NewDocumentDisplayBean> recorddocuments) {
        this.recorddocuments = recorddocuments;
    }

    public class RecordDisplayDto {
        //目前功能尚未开启

        //需要设置
        public String recordType;
        public int ownerId;
        public RemoteConsultationRecord remoteConsultationRecord = new RemoteConsultationRecord();

        public class RemoteConsultationRecord {
            //需要设置
            //住诉/现病史
            public String presentIllness;
            //患者系统医疗记录是否已阅
            public boolean isPatientHealthRecordReviewed;
            //目前主要问题
            public String currentHealthIssues;
            //目前所接收治疗
            public String currentTreatment;
            //印象/咨询建议
            public String remoteConsultationDoctorComment;
            //目前功能尚未开启

        }
    }

    private class DepartmentDisplayDto {
        public int departmentId;
        public String departmentName;
    }
}
