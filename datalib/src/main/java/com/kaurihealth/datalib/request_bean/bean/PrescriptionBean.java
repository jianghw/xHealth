package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PrescriptionBean {

    /**
     * prescriptionId : 3081
     * patientId : 4
     * type : 处方药
     * date : 2016-01-22T00:00:00
     * prescriptionDetail : 无
     * duration : 0
     * doctor : 姜66
     * departmentId : 7
     * departmentName : 心内科
     * hospital : 啊锕锕锕锕锕锕锕锕锕
     * comment :
     * createdDate : 2016-01-26T00:00:00
     * modifiedDate : 2016-01-26T00:00:00
     * createdBy : 2
     * modifiedBy : 2
     * PrescriptionDocuments : [{"prescriptionImageId":10,"prescriptionId":3081,"imageUrl":"http://kaurihealthrecordimagetest.kaurihealth.com/4/处方/4c4ef5c09cc543b8bf74b31a3f5e4da4.Desert.jpg","imageFormat":"jpeg","fileName":"4c4ef5c09cc543b8bf74b31a3f5e4da4.Desert.jpg","comment":"","imageDetail":null,"imageDataString":null,"isDeleted":false}]
     */

    private int prescriptionId;
    private int patientId;
    private String type;
    private String date;
    private String prescriptionDetail;
    private int duration;
    private String doctor;
    private int departmentId;
    private String departmentName;
    private String hospital;
    private String comment;
    private String createdDate;
    private String modifiedDate;
    private int createdBy;
    private int modifiedBy;
    /**
     * prescriptionImageId : 10
     * prescriptionId : 3081
     * imageUrl : http://kaurihealthrecordimagetest.kaurihealth.com/4/处方/4c4ef5c09cc543b8bf74b31a3f5e4da4.Desert.jpg
     * imageFormat : jpeg
     * fileName : 4c4ef5c09cc543b8bf74b31a3f5e4da4.Desert.jpg
     * comment :
     * imageDetail : null
     * imageDataString : null
     * isDeleted : false
     */

    private List<PrescriptionDocumentsEntity> prescriptionDocuments;

    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrescriptionDetail(String prescriptionDetail) {
        this.prescriptionDetail = prescriptionDetail;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public void setModifiedBy(int modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public void setPrescriptionDocuments(List<PrescriptionDocumentsEntity> prescriptionDocuments) {
        this.prescriptionDocuments = prescriptionDocuments;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getPrescriptionDetail() {
        return prescriptionDetail;
    }

    public int getDuration() {
        return duration;
    }

    public String getDoctor() {
        return doctor;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getHospital() {
        return hospital;
    }

    public String getComment() {
        return comment;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public int getModifiedBy() {
        return modifiedBy;
    }

    public List<PrescriptionDocumentsEntity> getPrescriptionDocuments() {
        return prescriptionDocuments;
    }

    public static class PrescriptionDocumentsEntity {
        private int prescriptionDocumentId;
        private int prescriptionId;
        private String documentUrl;
        private String documentFormat;
        private String fileName;
        private String comment;
        private Object displayName;
        private boolean isDeleted;

        public void setPrescriptionDocumentId(int prescriptionDocumentId) {
            this.prescriptionDocumentId = prescriptionDocumentId;
        }

        public void setPrescriptionId(int prescriptionId) {
            this.prescriptionId = prescriptionId;
        }

        public void setDocumentUrl(String documentUrl) {
            this.documentUrl = documentUrl;
        }

        public void setDocumentFormat(String documentFormat) {
            this.documentFormat = documentFormat;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setDisplayName(Object displayName) {
            this.displayName = displayName;
        }

        public void setIsDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
        }

        public int getPrescriptionImageId() {
            return prescriptionDocumentId;
        }

        public int getPrescriptionId() {
            return prescriptionId;
        }

        public String getdocumentUrl() {
            return documentUrl;
        }

        public String getoDcumentFormat() {
            return documentFormat;
        }

        public String getFileName() {
            return fileName;
        }

        public String getComment() {
            return comment;
        }

        public Object getDisplayName() {
            return displayName;
        }

        public boolean isIsDeleted() {
            return isDeleted;
        }
    }
}
