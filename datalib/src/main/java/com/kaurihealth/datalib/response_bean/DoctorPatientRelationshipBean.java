package com.kaurihealth.datalib.response_bean;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：医患关系
 */
public class DoctorPatientRelationshipBean implements Serializable{

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
    private int doctorPatientId;   //医患关系ID
    private int doctorId; //医生ID
    private int patientId;   //患者ID
    private boolean isActive; //0：代表还在服务期间（进行中） 1：代表服务已经结束
    private boolean isWaitingForPatientConfirm;     //是否等待患者确认结束关系
    private boolean isPatientConfirmed;   //患者是否确认结束关系
    private String relationship;  //关系（0：普通，1：专属，2：转诊，3：协作）
    private DoctorDisplayBean doctor; //医生的参数

    public boolean isCheck;

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

    private PatientDisplayBean patient;  //患者的参数
    private Date requestDate; //请求时间
    private Date startDate; //开始时间
    private Date endDate; //结束时间
    private String comment; //请求原因、备注
    private String relationshipReason; //医患关系发起的原因


    public String getReferralDoctor() {
        return referralDoctor;
    }

    public void setReferralDoctor(String referralDoctor) {
        this.referralDoctor = referralDoctor;
    }

    public Date getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(Date referralDate) {
        this.referralDate = referralDate;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    private String referralDoctor;//转诊人
    private Date referralDate;//转诊时间
    private String referralReason;//转诊原因

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
        } else if(relationshipReason.equalsIgnoreCase("转诊")){
            return relationshipReason + "患者服务";
        }else{
            return relationshipReason + "服务";
        }
    }

    public void setRelationshipReason(String relationshipReason) {
        this.relationshipReason = relationshipReason;
    }

}
