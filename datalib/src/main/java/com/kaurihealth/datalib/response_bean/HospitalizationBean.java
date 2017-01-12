package com.kaurihealth.datalib.response_bean;

import com.kaurihealth.utilslib.image.HospitalizationDocumentsBean;

import java.util.Date;
import java.util.List;

/**
 * Created by jianghw on 2016/12/21.
 * <p/>
 * Describe:住院记录
 */
public class HospitalizationBean {
    private int hospitalizationId;
    private int patientRecordId;
    private String hospitalizationType;
    private Date createdDate;
    private String doctor;
    private String hospital;
    private int departmentId;
    private boolean isDeleted;
    /**
     * departmentId : 1
     * departmentName : sample string 2
     * level : 3
     * parent : 1
     */

    private DepartmentDisplayBean department;
    private String comment;
    private String hospitalNumber;
    /**
     * hospitalizationDocumentId : 1
     * hospitalizationId : 2
     * documentUrl : sample string 3
     * documentFormat : sample string 4
     * fileName : sample string 5
     * comment : sample string 6
     * isDeleted : true
     * displayName : sample string 8
     */

    private List<HospitalizationDocumentsBean> hospitalizationDocuments;

    public int getHospitalizationId() {
        return hospitalizationId;
    }

    public void setHospitalizationId(int hospitalizationId) {
        this.hospitalizationId = hospitalizationId;
    }

    public int getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(int patientRecordId) {
        this.patientRecordId = patientRecordId;
    }

    public String getHospitalizationType() {
        return hospitalizationType;
    }

    public void setHospitalizationType(String hospitalizationType) {
        this.hospitalizationType = hospitalizationType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public DepartmentDisplayBean getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDisplayBean department) {
        this.department = department;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getHospitalNumber() {
        return hospitalNumber;
    }

    public void setHospitalNumber(String hospitalNumber) {
        this.hospitalNumber = hospitalNumber;
    }

    public List<HospitalizationDocumentsBean> getHospitalizationDocuments() {
        return hospitalizationDocuments;
    }

    public void setHospitalizationDocuments(List<HospitalizationDocumentsBean> hospitalizationDocuments) {
        this.hospitalizationDocuments = hospitalizationDocuments;
    }

}
