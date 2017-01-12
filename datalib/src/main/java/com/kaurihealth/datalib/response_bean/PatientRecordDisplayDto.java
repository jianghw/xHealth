package com.kaurihealth.datalib.response_bean;

import com.kaurihealth.utilslib.image.RecordDocumentsBean;

import java.util.Date;
import java.util.List;

/**
 * Created by jianghw on 2016/12/21.
 * <p/>
 * Describe:GET api/PatientRecord/LoadAllPatientGenericRecordsBypatientIdAndCategory?patientId={patientId}&category={category}
 * 通过患者Id和病例类型查询该患者对应的的所有病例
 */

public class PatientRecordDisplayDto {

    /**
     * patientRecordId : 1
     * patientId : 2
     * comment : sample string 3
     * createdDate : 2016-12-21T14:57:59+08:00
     * createdBy : 5
     * modifiedDate : 2016-12-21T14:57:59+08:00
     * recordDate : 2016-12-21T14:57:59+08:00
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
     * hospitalization : [{"hospitalizationId":1,"patientRecordId":2,"hospitalizationType":"sample string 3","createdDate":"2016-12-21T14:57:59+08:00","doctor":"sample string 5","hospital":"sample string 6","departmentId":7,"isDeleted":true,"hospitalizationDocuments":[{"hospitalizationDocumentId":1,"hospitalizationId":2,"documentUrl":"sample string 3","documentFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","isDeleted":true,"displayName":"sample string 8"},{"hospitalizationDocumentId":1,"hospitalizationId":2,"documentUrl":"sample string 3","documentFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","isDeleted":true,"displayName":"sample string 8"}],"department":{"departmentId":1,"departmentName":"sample string 2","level":3,"parent":1},"comment":"sample string 9","hospitalNumber":"sample string 10"},{"hospitalizationId":1,"patientRecordId":2,"hospitalizationType":"sample string 3","createdDate":"2016-12-21T14:57:59+08:00","doctor":"sample string 5","hospital":"sample string 6","departmentId":7,"isDeleted":true,"hospitalizationDocuments":[{"hospitalizationDocumentId":1,"hospitalizationId":2,"documentUrl":"sample string 3","documentFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","isDeleted":true,"displayName":"sample string 8"},{"hospitalizationDocumentId":1,"hospitalizationId":2,"documentUrl":"sample string 3","documentFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","isDeleted":true,"displayName":"sample string 8"}],"department":{"departmentId":1,"departmentName":"sample string 2","level":3,"parent":1},"comment":"sample string 9","hospitalNumber":"sample string 10"}]
     * department : {"departmentId":1,"departmentName":"sample string 2","level":3,"parent":1}
     * isDeleted : true
     * status : sample string 15
     * isNew : true
     * recordDocuments : [{"recordDocumentId":1,"documentUrl":"sample string 2","documentFormat":"sample string 3","fileName":"sample string 4","displayName":"sample string 5","comment":"sample string 6","isDeleted":true,"patientRecordId":8},{"recordDocumentId":1,"documentUrl":"sample string 2","documentFormat":"sample string 3","fileName":"sample string 4","displayName":"sample string 5","comment":"sample string 6","isDeleted":true,"patientRecordId":8}]
     * sickness : [{"patientRecordSicknessId":1,"patientRecordId":2,"sicknessId":1,"sicknessName":"sample string 3"},{"patientRecordSicknessId":1,"patientRecordId":2,"sicknessId":1,"sicknessName":"sample string 3"}]
     */
    public String mark;
    private int patientRecordId;
    private int patientId;
    private String comment;
    private Date createdDate;
    private int createdBy;
    private Date modifiedDate;
    private Date recordDate;
    private int modifiedBy;
    private String doctor;
    private String hospital;
    private int departmentId;
    private String category;
    private String subject;
    /**
     * recordId : 1
     * recordType : sample string 2
     * isValid : true
     * ownerId : 1
     * patientRecordId : 4
     * outpatientRecord : {"outPatientRecordId":1,"presentIllness":"sample string 2","isPastIllnessReviewed":true,"isPastIllnessUpToDate":true,"hrValue":1,"upperBpValue":1,"lowerBpValue":1,"rrValue":1,"tValue":1,"physicalExamination":"sample string 5","isWorkupReviewed":true,"isCurrentMedicationReviewed":true,"isCurrentMedicationUpToDate":true,"isLongTermMonitoringDataReviewed":true,"workupReviewComment":"sample string 10","treatmentAndPlan":"sample string 11","recordId":12}
     * remoteConsultationRecord : {"remoteConsultationRecordId":1,"presentIllness":"sample string 2","isPatientHealthRecordReviewed":true,"currentHealthIssues":"sample string 4","currentTreatment":"sample string 5","remoteConsultationDoctorComment":"sample string 6","recordId":7}
     * onlineConsultationRecord : {"onlineConsultationRecordId":1,"onlineConsultationPurpose":"sample string 2","majorIssue":"sample string 3","onlineConsultationDoctorComment":"sample string 4","recordId":5,"currentTreatment":"sample string 6","presentIllness":"sample string 7"}
     */

    private RecordBean record;
    /**
     * labTestId : 1
     * patientRecordId : 2
     * labTestType : sample string 3
     */

    private LabTestBean labTest;
    /**
     * supplementaryTestId : 1
     * patientRecordId : 2
     */

    private SupplementaryTestBean supplementaryTest;
    /**
     * pathologyRecordId : 1
     * patientRecordId : 2
     */

    private PathologyRecordBean pathologyRecord;
    /**
     * departmentId : 1
     * departmentName : sample string 2
     * level : 3
     * parent : 1
     */

    private DepartmentDisplayBean department;
    private boolean isDeleted;
    private String status;
    private boolean isNew;
    /**
     * hospitalizationId : 1
     * patientRecordId : 2
     * hospitalizationType : sample string 3
     * createdDate : 2016-12-21T14:57:59+08:00
     * doctor : sample string 5
     * hospital : sample string 6
     * departmentId : 7
     * isDeleted : true
     * hospitalizationDocuments : [{"hospitalizationDocumentId":1,"hospitalizationId":2,"documentUrl":"sample string 3","documentFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","isDeleted":true,"displayName":"sample string 8"},{"hospitalizationDocumentId":1,"hospitalizationId":2,"documentUrl":"sample string 3","documentFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","isDeleted":true,"displayName":"sample string 8"}]
     * department : {"departmentId":1,"departmentName":"sample string 2","level":3,"parent":1}
     * comment : sample string 9
     * hospitalNumber : sample string 10
     */

    private List<HospitalizationBean> hospitalization;
    /**
     * recordDocumentId : 1
     * documentUrl : sample string 2
     * documentFormat : sample string 3
     * fileName : sample string 4
     * displayName : sample string 5
     * comment : sample string 6
     * isDeleted : true
     * patientRecordId : 8
     */

    private List<RecordDocumentsBean> recordDocuments;
    /**
     * patientRecordSicknessId : 1
     * patientRecordId : 2
     * sicknessId : 1
     * sicknessName : sample string 3
     */

    private List<SicknessBean> sickness;

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

    public List<HospitalizationBean> getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(List<HospitalizationBean> hospitalization) {
        this.hospitalization = hospitalization;
    }

    public List<RecordDocumentsBean> getRecordDocuments() {
        return recordDocuments;
    }

    public void setRecordDocuments(List<RecordDocumentsBean> recordDocuments) {
        this.recordDocuments = recordDocuments;
    }

    public List<SicknessBean> getSickness() {
        return sickness;
    }

    public void setSickness(List<SicknessBean> sickness) {
        this.sickness = sickness;
    }

}
