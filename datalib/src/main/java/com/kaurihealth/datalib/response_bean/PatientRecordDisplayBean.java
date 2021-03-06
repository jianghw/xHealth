package com.kaurihealth.datalib.response_bean;


import com.kaurihealth.utilslib.image.RecordDocumentsBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 通过患者ID查询所有的患者的病例 医生和患者的token都可以用
 * 备注：更新患者临床诊疗记录 医生和患者的token都可以
 */
public class PatientRecordDisplayBean implements Serializable{

    /**
     * patientRecordId : 1
     * patientId : 2
     * comment : sample string 3
     * createdDate : 2016-09-08T13:10:17+08:00
     * createdBy : 5
     * modifiedDate : 2016-09-08T13:10:17+08:00
     * recordDate : 2016-09-08T13:10:17+08:00
     * modifiedBy : 8
     * doctor : sample string 9
     * hospital : sample string 10
     * departmentId : 11
     * category : sample string 12
     * subject : sample string 13
     * record : {"recordId":1,"recordType":"sample string 2","isValid":true,"ownerId":1,"patientRecordId":4,"outpatientRecord":{"outPatientRecordId":1,"presentIllness":"sample string 2","isPastIllnessReviewed":true,"isPastIllnessUpToDate":true,"hrValue":1,"upperBpValue":1,"lowerBpValue":1,"rrValue":1,"tValue":1,"physicalExamination":"sample string 5","isWorkupReviewed":true,"isCurrentMedicationReviewed":true,"isCurrentMedicationUpToDate":true,"isLongTermMonitoringDataReviewed":true,"workupReviewComment":"sample string 10","treatmentAndPlan":"sample string 11","recordId":12},"remoteConsultationRecord":{"remoteConsultationRecordId":1,"presentIllness":"sample string 2","isPatientHealthRecordReviewed":true,"currentHealthIssues":"sample string 4","currentTreatment":"sample string 5","remoteConsultationDoctorComment":"sample string 6","recordId":7},"onlineConsultationRecord":{"onlineConsultationRecordId":1,"onlineConsultationPurpose":"sample string 2","majorIssue":"sample string 3","onlineConsultationDoctorComment":"sample string 4","recordId":5,"currentTreatment":"sample string 6","presentIllness":"sample string 7"}}
     * labTest : {"labTestId":1,"patientRecordId":2,"labTestType":"sample string 3"}
     * supplementaryTest : {"supplementaryTestId":1,"patientRecordId":2}
     * pathologyRecord : {"pathologyRecordId":1,"patientRecordId":2}
     * department : {"departmentId":1,"departmentName":"sample string 2","level":3,"parent":1}
     * isDeleted : true
     * status : sample string 15
     * isNew : true
     * recordDocuments : [{"recordDocumentId":1,"documentUrl":"sample string 2","documentFormat":"sample string 3","fileName":"sample string 4","displayName":"sample string 5","comment":"sample string 6","isDeleted":true,"patientRecordId":8},{"recordDocumentId":1,"documentUrl":"sample string 2","documentFormat":"sample string 3","fileName":"sample string 4","displayName":"sample string 5","comment":"sample string 6","isDeleted":true,"patientRecordId":8}]
     */
    private int patientRecordId;  //患者记录ID
    private int patientId;    //患者ID
    private String comment;  //留言，备注
    private Date createdDate;   //创建时间
    private int createdBy;  //创建者
    private Date modifiedDate;  //修改时间
    private Date recordDate; //记录时间
    private int modifiedBy; //修改者
    private String doctor;  //医生
    private String hospital;  //医院
    private int departmentId; //科室ID


    //类别（0：临床诊疗，1：实验室检查，2：辅助检查，3：病理）
    private String category;    //类别


    //主题（其它 = 0,实验室检查 = 1,辅助检查 = 2,病理 = 3,门诊记录电子病历 = 4,远程医疗咨询 = 5,
    //网络医疗咨询 = 6,入院记录 = 7,协作医疗咨询 = 9,院内治疗相关记录 = 10,影像学检查 = 11,
    //心血管系统相关检查 = 12,血液 = 14,大便 = 16,临床指导记录 = 17,常规血液学检查 = 18,
    //常规尿液检查 = 19,常规粪便检查 = 20,特殊检查 = 21,门诊记录图片存档 = 22）
    private String subject;  //主题
    /**
     * 病例的参数
     * recordId : 1
     * recordType : sample string 2
     * isValid : true
     * ownerId : 1
     * patientRecordId : 4
     * outpatientRecord : {"outPatientRecordId":1,"presentIllness":"sample string 2","isPastIllnessReviewed":true,"isPastIllnessUpToDate":true,"hrValue":1,"upperBpValue":1,"lowerBpValue":1,"rrValue":1,"tValue":1,"physicalExamination":"sample string 5","isWorkupReviewed":true,"isCurrentMedicationReviewed":true,"isCurrentMedicationUpToDate":true,"isLongTermMonitoringDataReviewed":true,"workupReviewComment":"sample string 10","treatmentAndPlan":"sample string 11","recordId":12}
     * remoteConsultationRecord : {"remoteConsultationRecordId":1,"presentIllness":"sample string 2","isPatientHealthRecordReviewed":true,"currentHealthIssues":"sample string 4","currentTreatment":"sample string 5","remoteConsultationDoctorComment":"sample string 6","recordId":7}
     * onlineConsultationRecord : {"onlineConsultationRecordId":1,"onlineConsultationPurpose":"sample string 2","majorIssue":"sample string 3","onlineConsultationDoctorComment":"sample string 4","recordId":5,"currentTreatment":"sample string 6","presentIllness":"sample string 7"}
     */
    private RecordBean record;   //病例的参数    	RecordDisplayDto
    /**
     * 实验室测试的参数
     * labTestId : 1
     * patientRecordId : 2
     * labTestType : sample string 3
     */
    private LabTestBean labTest;   //实验室测试的参数   //LabTestDisplayDto
    /**
     * 补充测试的参数
     * supplementaryTestId : 1
     * patientRecordId : 2
     */
    private SupplementaryTestBean supplementaryTest;  //补充测试的参数    	SupplementaryTestDisplayDto
    /**
     * 病理的参数
     * pathologyRecordId : 1
     * patientRecordId : 2
     */
    private PathologyRecordBean pathologyRecord;   //病理的参数    PathologyRecordDisplayDto
    /**
     * 科室的参数
     * departmentId : 1
     * departmentName : sample string 2
     * level : 3
     * parent : 1
     */
    private DepartmentDisplayBean department;  //科室的参数
    private boolean isDeleted;
    private String status;
    private boolean isNew;
    /**
     * 病例图像的参数
     * recordDocumentId : 1
     * documentUrl : sample string 2
     * documentFormat : sample string 3
     * fileName : sample string 4
     * displayName : sample string 5
     * comment : sample string 6
     * isDeleted : true
     * patientRecordId : 8
     */
    private List<RecordDocumentsBean> recordDocuments;  //病例图像的参数

    public int getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(int patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public RecordBean getRecord() {
        return record;
    }

    public void setRecord(RecordBean record) {
        this.record = record;
    }

    public LabTestBean getLabTest() {
        return labTest;
    }

    public void setLabTest(LabTestBean labTest) {
        this.labTest = labTest;
    }

    public SupplementaryTestBean getSupplementaryTest() {
        return supplementaryTest;
    }

    public void setSupplementaryTest(SupplementaryTestBean supplementaryTest) {
        this.supplementaryTest = supplementaryTest;
    }

    public PathologyRecordBean getPathologyRecord() {
        return pathologyRecord;
    }

    public void setPathologyRecord(PathologyRecordBean pathologyRecord) {
        this.pathologyRecord = pathologyRecord;
    }

    public DepartmentDisplayBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDisplayBean department) {
        this.department = department;
    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public List<RecordDocumentsBean> getRecordDocuments() {
        return recordDocuments;
    }

    public void setRecordDocuments(List<RecordDocumentsBean> recordDocuments) {
        this.recordDocuments = recordDocuments;
    }

}
