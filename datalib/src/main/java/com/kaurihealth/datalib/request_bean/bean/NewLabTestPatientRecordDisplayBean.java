package com.kaurihealth.datalib.request_bean.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by mip on 21/07/2016.
 */
public class NewLabTestPatientRecordDisplayBean {
    /**
     * patientId : 1
     * comment : sample string 2
     * recordDate : 2016-09-29T11:41:50+08:00
     * doctor : sample string 4
     * hospital : sample string 5
     * departmentId : 6
     * subject : sample string 7
     * labTest : {"labTestType":"sample string 1"}
     * status : sample string 8
     * recordDocuments : [{"documentUrl":"sample string 1","documentFormat":"sample string 2","fileName":"sample string 3","displayName":"sample string 4","comment":"sample string 5"},{"documentUrl":"sample string 1","documentFormat":"sample string 2","fileName":"sample string 3","displayName":"sample string 4","comment":"sample string 5"}]
     */

    private int patientId;
    private String comment;
    private Date recordDate;
    private String doctor;
    private String hospital;
    private int departmentId;
    private String subject;
    /**
     * labTestType : sample string 1
     */

    private LabTestBean labTest;
    private String status;
    /**
     * documentUrl : sample string 1
     * documentFormat : sample string 2
     * fileName : sample string 3
     * displayName : sample string 4
     * comment : sample string 5
     */

    private List<RecordDocumentsBean> recordDocuments;

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

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LabTestBean getLabTest() {
        return labTest;
    }

    public void setLabTest(LabTestBean labTest) {
        this.labTest = labTest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<RecordDocumentsBean> getRecordDocuments() {
        return recordDocuments;
    }

    public void setRecordDocuments(List<RecordDocumentsBean> recordDocuments) {
        this.recordDocuments = recordDocuments;
    }

    public static class LabTestBean {
        private String labTestType;

        public String getLabTestType() {
            return labTestType;
        }

        public void setLabTestType(String labTestType) {
            this.labTestType = labTestType;
        }
    }

    public static class RecordDocumentsBean {
        private String documentUrl;
        private String documentFormat;
        private String fileName;
        private String displayName;
        private String comment;

        public String getDocumentUrl() {
            return documentUrl;
        }

        public void setDocumentUrl(String documentUrl) {
            this.documentUrl = documentUrl;
        }

        public String getDocumentFormat() {
            return documentFormat;
        }

        public void setDocumentFormat(String documentFormat) {
            this.documentFormat = documentFormat;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

//    @JsonIgnore
//    public static int errorDefault = -10;
//    public Integer patientId;
//    public String comment;
//    public String recordDate;
//    public String doctor;
//    public String hospital;
//    public Integer departmentId = errorDefault;
//    public String subject;
//    public NewLabTestDetailDisplayBean labTest;
//    public String status;
//    public List<NewDocumentDisplayBean> recordDocuments;
}
