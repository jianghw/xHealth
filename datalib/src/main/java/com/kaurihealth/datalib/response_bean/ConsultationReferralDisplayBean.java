package com.kaurihealth.datalib.response_bean;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by mip on 2016/10/24.
 */

public class ConsultationReferralDisplayBean implements Serializable{

    /**
     * 转诊id
     */
    private int consultationReferralId;
    /**
     * 主治医生id
     */
    private int sourceDoctorId;
    /**
     * 转诊医生id
     */
    private int destinationDoctorId;
    /**
     * 时间
     */
    private Date date;
    /**
     * 转诊原因/备注
     */
    private String comment;
    /**
     * 主治医生的参数
     */
    private DoctorDisplayBean sourceDoctor;
    /**
     * 转诊医生的参数
     */
    private DoctorDisplayBean destinationDoctor;
    /**
     * 患者请求id
     */
    private int patientRequestId;
    /**
     * 预约时已存在的医患关系id
     */
    private int doctorPatientRelationshipId;
    /**
     * 转诊记录状态(0：等待，1：接受，2：拒绝)
     */
    private String status;
    /**
     * 患者id
     */
    private int patientId;
    /**
     * 患者请求对象
     */
    private PatientRequestDisplayBean patientRequest;

    public DoctorPatientRelationshipBean getDoctorPatientRelationship() {
        return doctorPatientRelationship;
    }

    public void setDoctorPatientRelationship(DoctorPatientRelationshipBean doctorPatientRelationship) {
        this.doctorPatientRelationship = doctorPatientRelationship;
    }

    public PatientRequestDisplayBean getPatientRequest() {
        return patientRequest;
    }

    public void setPatientRequest(PatientRequestDisplayBean patientRequest) {
        this.patientRequest = patientRequest;
    }

    public int getPatientRequestId() {
        return patientRequestId;
    }

    public void setPatientRequestId(int patientRequestId) {
        this.patientRequestId = patientRequestId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorPatientRelationshipId() {
        return doctorPatientRelationshipId;
    }

    public void setDoctorPatientRelationshipId(int doctorPatientRelationshipId) {
        this.doctorPatientRelationshipId = doctorPatientRelationshipId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DoctorDisplayBean getDestinationDoctor() {
        return destinationDoctor;
    }

    public void setDestinationDoctor(DoctorDisplayBean destinationDoctor) {
        this.destinationDoctor = destinationDoctor;
    }

    public DoctorDisplayBean getSourceDoctor() {
        return sourceDoctor;
    }

    public void setSourceDoctor(DoctorDisplayBean sourceDoctor) {
        this.sourceDoctor = sourceDoctor;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDestinationDoctorId() {
        return destinationDoctorId;
    }

    public void setDestinationDoctorId(int destinationDoctorId) {
        this.destinationDoctorId = destinationDoctorId;
    }

    public int getSourceDoctorId() {
        return sourceDoctorId;
    }

    public void setSourceDoctorId(int sourceDoctorId) {
        this.sourceDoctorId = sourceDoctorId;
    }

    public int getConsultationReferralId() {
        return consultationReferralId;
    }

    public void setConsultationReferralId(int consultationReferralId) {
        this.consultationReferralId = consultationReferralId;
    }

    /**
     * 预约时已存在的医患关系
     */
    DoctorPatientRelationshipBean doctorPatientRelationship;
}
