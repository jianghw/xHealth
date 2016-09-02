package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class AddOnlineConsultationRecordBean {
    //默认
    public String status = "草稿";
    //需要设置
    public int patientId;
    public String comment;
    public String recordDate;
    public String doctor;
    public String hospital;
    public int departmentId;
    public String subject;
    public RecordDisplayDto record = new RecordDisplayDto();
    public List<NewDocumentDisplayBean> recorddocuments;

    /**
     * @param patientId                       患者Id
     * @param comment                         留言
     * @param recordDate                      记录的日期
     * @param doctor                          医生姓名
     * @param hospital                        医院名称
     * @param departmentId                    机构ID
     * @param subject
     * @param onlineConsultationPurpose       网路咨询原因
     * @param majorIssue                      目前主要问题
     * @param onlineConsultationDoctorComment 印象/咨询建议
     * @param currentTreatment                目前所接收治疗
     * @param presentIllness                  主诉/现病史
     */
    public AddOnlineConsultationRecordBean(int patientId, String comment,
                                           String recordDate, String doctor,
                                           String hospital, int departmentId,
                                           String subject, String onlineConsultationPurpose,
                                           String majorIssue, String onlineConsultationDoctorComment,
                                           String currentTreatment, String presentIllness,
                                           List<NewDocumentDisplayBean> recorddocuments,
                                           Status status) {
        this.patientId = patientId;
        this.comment = comment;
        this.recordDate = recordDate;
        this.doctor = doctor;
        this.hospital = hospital;
        this.departmentId = departmentId;
        this.subject = subject;
        record.ownerId = patientId;
        record.recordType = subject;
        record.onlineConsultationRecord.onlineConsultationPurpose = onlineConsultationPurpose;
        record.onlineConsultationRecord.majorIssue = majorIssue;
        record.onlineConsultationRecord.onlineConsultationDoctorComment = onlineConsultationDoctorComment;
        record.onlineConsultationRecord.currentTreatment = currentTreatment;
        record.onlineConsultationRecord.presentIllness = presentIllness;
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
        public OnlineConsultationRecord onlineConsultationRecord = new OnlineConsultationRecord();

        public class OnlineConsultationRecord {
            public String onlineConsultationPurpose;
            public String majorIssue;
            public String onlineConsultationDoctorComment;
            public String currentTreatment;
            public String presentIllness;

        }
    }
}
