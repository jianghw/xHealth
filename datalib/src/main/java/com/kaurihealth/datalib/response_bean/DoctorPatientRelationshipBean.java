package com.kaurihealth.datalib.response_bean;

import android.text.TextUtils;

import java.util.Date;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：
 */
public class DoctorPatientRelationshipBean {

    /**
     * doctorPatientId : 3649
     * doctorId : 506
     * patientId : 44428
     * isActive : true
     * isWaitingForPatientConfirm : null
     * isPatientConfirmed : null
     * relationship : 普通
     * doctor : null
     * patient : {"patientId":44428,"nationalIdentity":"null","kauriHealthId":"29d1169b1f164236a45e4f4d58e35edd20160818101454","avatar":"","nationalMedicalInsuranceNumber":"null","mobileNumber":"null","homePhoneNumber":"null","emergencyContactNumber":"null","emergencyContactName":null,"emergencyContactRelationship":null,"patientType":"注册病人","userId":133823,"firstName":"。","lastName":"好","fullName":"好。","gender":"男","dateOfBirth":"1978-01-01T00:00:00+08:00","email":"","address":null,"addressId":null,"isActive":true,"totalCredit":0,"availableCredit":0,"allowUnknownMessage":true,"allergyDetail":null}
     * requestDate : 2016-08-18T10:14:55+08:00
     * startDate : 2016-08-18T10:14:55+08:00
     * endDate : 2016-08-20T10:14:55+08:00
     * comment :
     * relationshipReason : 门诊
     */

    private int doctorPatientId;
    private int doctorId;
    private int patientId;
    private boolean isActive;
    private boolean isWaitingForPatientConfirm;
    private boolean isPatientConfirmed;
    private String relationship;
    private DoctorDisplayBean doctor;
    /**
     * patientId : 44428
     * nationalIdentity : null
     * kauriHealthId : 29d1169b1f164236a45e4f4d58e35edd20160818101454
     * avatar :
     * nationalMedicalInsuranceNumber : null
     * mobileNumber : null
     * homePhoneNumber : null
     * emergencyContactNumber : null
     * emergencyContactName : null
     * emergencyContactRelationship : null
     * patientType : 注册病人
     * userId : 133823
     * firstName : 。
     * lastName : 好
     * fullName : 好。
     * gender : 男
     * dateOfBirth : 1978-01-01T00:00:00+08:00
     * email :
     * address : null
     * addressId : null
     * isActive : true
     * totalCredit : 0.0
     * availableCredit : 0.0
     * allowUnknownMessage : true
     * allergyDetail : null
     */

    private PatientDisplayBean patient;
    private Date requestDate;
    private Date startDate;
    private Date endDate;
    private String comment;
    private String relationshipReason;

    public int getDoctorPatientId() {
        return doctorPatientId;
    }

    public void setDoctorPatientId(int doctorPatientId) {
        this.doctorPatientId = doctorPatientId;
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

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsWaitingForPatientConfirm() {
        return isWaitingForPatientConfirm;
    }

    public void setIsWaitingForPatientConfirm(boolean isWaitingForPatientConfirm) {
        this.isWaitingForPatientConfirm = isWaitingForPatientConfirm;
    }

    public boolean getIsPatientConfirmed() {
        return isPatientConfirmed;
    }

    public void setIsPatientConfirmed(boolean isPatientConfirmed) {
        this.isPatientConfirmed = isPatientConfirmed;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRelationshipReason() {
        return relationshipReason;
    }

    public String getModifyRelationshipReason() {
        if (TextUtils.isEmpty(this.relationshipReason)) return "暂无";
        if (relationshipReason.equalsIgnoreCase("门诊")) {
            return "门诊患者";
        } else {
            return relationshipReason + "服务";
        }
    }

    public void setRelationshipReason(String relationshipReason) {
        this.relationshipReason = relationshipReason;
    }

}
