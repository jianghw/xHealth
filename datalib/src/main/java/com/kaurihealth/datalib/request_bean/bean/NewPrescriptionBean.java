package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * Created by Nick on 21/07/2016.
 */
public class NewPrescriptionBean {
    /**
     * patientId : 1
     * type : sample string 2
     * date : 2016-03-16T09:21:27.6300294+08:00
     * prescriptionDetail : sample string 4
     * duration : 1
     * doctor : sample string 5
     * departmentId : 6
     * hospital : sample string 7
     * comment : sample string 8
     * prescriptionImages : [{"prescriptionImageId":1,"prescriptionId":2,"imageUrl":"sample string 3","imageFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","imageDetail":"QEA=","imageDataString":"sample string 7","isDeleted":true},{"prescriptionImageId":1,"prescriptionId":2,"imageUrl":"sample string 3","imageFormat":"sample string 4","fileName":"sample string 5","comment":"sample string 6","imageDetail":"QEA=","imageDataString":"sample string 7","isDeleted":true}]
     */

    private int patientId;
    private String type;
    private String date;
    private String prescriptionDetail;
    private int duration;
    private String doctor;
    private int departmentId;
    private String hospital;
    private String comment;

    private List<NewDocumentDisplayBean> prescriptionDocuments;

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

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setPrescriptionImages(List<NewDocumentDisplayBean> prescriptionDocuments) {
        this.prescriptionDocuments = prescriptionDocuments;
    }

    public List<NewDocumentDisplayBean> getPrescriptionDocuments() {
        return prescriptionDocuments;
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

    public String getHospital() {
        return hospital;
    }

    public String getComment() {
        return comment;
    }

}
