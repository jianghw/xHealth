package com.kaurihealth.datalib.request_bean.bean;


import com.kaurihealth.datalib.response_bean.ConsultationReferralDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 22/04/2016.
 */
public class PatientRequestDisplayBean implements Serializable {
    public int getPatientRequestId() {
        return patientRequestId;
    }

    public void setPatientRequestId(int patientRequestId) {
        this.patientRequestId = patientRequestId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestReason() {
        return requestReason;
    }

    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getDoctorComment() {
        return doctorComment;
    }

    public void setDoctorComment(String doctorComment) {
        this.doctorComment = doctorComment;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(int consultationId) {
        this.consultationId = consultationId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public DoctorDisplayBean getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorDisplayBean doctor) {
        this.doctor = doctor;
    }

    public PatientDisplayBean getPatient() {
        return patient;
    }

    public void setPatient(PatientDisplayBean patient) {
        this.patient = patient;
    }



    /// <summary>
    /// 患者请求ID
    /// </summary>
    public int patientRequestId;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 患者ID
    /// </summary>
    public int patientId;

    /// <summary>
    /// 请求类型(0:门诊，1：远程会诊，2：网络会诊，3：专属医生，4：转诊，5：普通医生)
    /// </summary>
    public String requestType;

    /// <summary>
    /// 状态(0:等待,1:进行,2:拒绝,3:接受)
    /// </summary>
    public String status;

    /// <summary>
    /// 请求的原因
    /// </summary>
    public String requestReason;

    /// <summary>
    /// 医生留言
    /// </summary>
    public String doctorComment;

    /// <summary>
    /// 创建时间
    /// </summary>
    public Date createdDate;

    /// <summary>
    /// 请求时间
    /// </summary>
    public Date requestDate;

    /// <summary>
    /// 回复日期
    /// </summary>
    public Date replyDate;

    /// <summary>
    /// 结束时间
    /// </summary>
    public Date endDate;

    /// <summary>
    /// 会诊ID
    /// </summary>
    public int consultationId;

    /// <summary>
    /// Gets or sets the order identifier.
    /// </summary>
    /// <value>
    /// The order identifier.
    /// </value>
    public int orderId;

    /// <summary>
    /// 医生所需的参数
    /// </summary>
    public DoctorDisplayBean doctor;

    /// <summary>
    /// 患者所需的参数
    /// </summary>
    public PatientDisplayBean patient;

    public ConsultationReferralDisplayBean getConsultationReferral() {
        return consultationReferral;
    }

    public void setConsultationReferral(ConsultationReferralDisplayBean consultationReferral) {
        consultationReferral = consultationReferral;
    }

    public ConsultationReferralDisplayBean consultationReferral;

}
