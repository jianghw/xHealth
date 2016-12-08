package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class AddOutpatientRecordBean {
    //默认
    public String status = "草稿";
    //目前功能尚未开启
    public Object recordImages = null;
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
     * @param patientId                   患者ID
     * @param comment                     备注
     * @param recordDate                  门诊时间
     * @param doctor                      医师
     * @param hospital                    机构
     * @param departmentId                科室ID
     * @param subject                     项目
     * @param departmentName              科室名称
     * @param presentIllness              主诉/现病史
     * @param isPastIllnessReviewed       既往病史    既往病史已阅
     * @param isPastIllnessUpToDate       既往病史    既往病史已更新
     * @param isCurrentMedicationReviewed 目前使用药物    患者系统病例记录已阅
     * @param isCurrentMedicationUpToDate 目前使用药物    患者系统病历记录已更新
     * @param workupReviewComment         体格检查备注
     * @param hrValue                     HR
     * @param rrValue                     RR
     * @param lowerBpValue                lowerBpValue
     * @param upperBpValue                upperBpValue
     * @param tValue                      tValue
     * @param workupReviewComment         体格检查备注
     * @param treatmentAndPlan            印象/计划
     */
    public AddOutpatientRecordBean(int patientId, String comment,
                                   String recordDate, String doctor,
                                   String hospital, int departmentId,
                                   String subject, String departmentName,
                                   String presentIllness, boolean isPastIllnessReviewed,
                                   boolean isPastIllnessUpToDate, boolean isCurrentMedicationReviewed,
                                   boolean isCurrentMedicationUpToDate, double hrValue,
                                   double upperBpValue, double lowerBpValue,
                                   double rrValue, double tValue,
                                   String physicalExamination,
                                   String treatmentAndPlan,
                                   List<NewDocumentDisplayBean> recorddocuments,
                                   Status status,
                                   String workupReviewComment,
                                   boolean isWorkupReviewed,
                                   boolean isLongTermMonitoringDataReviewed) {
        this.patientId = patientId;
        this.comment = comment;
        this.recordDate = recordDate;
        this.doctor = doctor;
        this.hospital = hospital;
        this.departmentId = departmentId;
        this.subject = subject;
        this.department.departmentId = departmentId;
        this.record.recordType = subject;
        this.record.ownerId = patientId;
        this.department.departmentName = departmentName;
        this.record.outpatientRecord.presentIllness = presentIllness;
        this.record.outpatientRecord.isPastIllnessReviewed = isPastIllnessReviewed;
        this.record.outpatientRecord.isPastIllnessUpToDate = isPastIllnessUpToDate;
        this.record.outpatientRecord.isCurrentMedicationReviewed = isCurrentMedicationReviewed;
        this.record.outpatientRecord.isCurrentMedicationUpToDate = isCurrentMedicationUpToDate;
        this.record.outpatientRecord.hrValue = (hrValue == -1 ? null : hrValue);
        this.record.outpatientRecord.upperBpValue = (upperBpValue == -1 ? null : upperBpValue);
        this.record.outpatientRecord.lowerBpValue = (lowerBpValue == -1 ? null : lowerBpValue);
        this.record.outpatientRecord.rrValue = (rrValue == -1 ? null : rrValue);
        this.record.outpatientRecord.tValue = (tValue == -1 ? null : tValue);
        this.record.outpatientRecord.physicalExamination = physicalExamination;
        this.record.outpatientRecord.treatmentAndPlan = treatmentAndPlan;
        this.recorddocuments = recorddocuments;
        this.status = status.value;
        this.record.outpatientRecord.workupReviewComment = workupReviewComment;
        this.record.outpatientRecord.isWorkupReviewed = isWorkupReviewed;
        this.record.outpatientRecord.isLongTermMonitoringDataReviewed = isLongTermMonitoringDataReviewed;
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
        public OutpatientRecordDetailDisplayDto outpatientRecord = new OutpatientRecordDetailDisplayDto();

        public class OutpatientRecordDetailDisplayDto {
            //默认
            public String physicalExamination;
            public int recordId = 0;
            //需要设置
            public String presentIllness;
            public boolean isPastIllnessReviewed;
            public boolean isPastIllnessUpToDate;
            public Double hrValue;
            public Double upperBpValue;
            public Double lowerBpValue;
            public Double rrValue;
            public Double tValue;
            public boolean isCurrentMedicationReviewed;
            public boolean isCurrentMedicationUpToDate;
            public String workupReviewComment;
            public boolean isWorkupReviewed;
            public boolean isLongTermMonitoringDataReviewed;
            public String treatmentAndPlan;
            //目前功能尚未开启

        }
    }

    private class DepartmentDisplayDto {
        public int departmentId;
        public String departmentName;
    }
}
