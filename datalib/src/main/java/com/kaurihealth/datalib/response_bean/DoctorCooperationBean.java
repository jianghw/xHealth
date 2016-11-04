package com.kaurihealth.datalib.response_bean;

import java.util.Date;

/**
 * Created by jianghw on 2016/9/19.
 * <p>
 * 描述：查询所有同意的协作医生
 */
public class DoctorCooperationBean {

    /**
     * doctorRelationshipId : 2595
     * sourceDoctorId : 11807
     * destinationDoctorId : 17428
     * status : 接受
     * createdDate : 2016-09-01T14:06:24+08:00
     * comment : 我是...
     * relatedDoctor : {"doctorId":11807,"avatar":"http://kaurihealthrecordimagetest.kaurihealth.com/ad7c58aa9b3a40749bc57d58e8c1a3f6/Icon/67cd43774fb0436aafcb8c249637d23b.jpeg","email":"null","workPhone":"null","mobilePhone":"null","isValidDoctor":false,"accountActivateDate":null,"doctorType":"普通医生","firstName":"sc","lastName":"wang","kauriHealthId":"ad7c58aa9b3a40749bc57d58e8c1a3f6","gender":"男","dateOfBirth":"1989-06-23T08:37:20+08:00","userId":33056,"registPercentage":100,"years":null,"practiceField":" kkkkkkkk","introduction":"null","fullName":"wangsc","totalCredit":0.01,"availableCredit":0,"allowUnknownMessage":true,"educationTitle":"博士生导师","mentorshipTitle":"教授","hospitalTitle":"主任医师","nationalIdentity":"","certificationNumber":"989884464566454","workingExperience":" Kkkllllllllllll","doctorInformations":{"doctorInformationId":635,"departmentId":40,"hospitalName":"天津市职工医院","doctorId":11807,"department":{"departmentId":40,"departmentName":"手显微外科","level":2,"parent":37},"outpatientAvalibility":null},"doctorEducation":null,"doctorGrades":[],"doctorCertifications":[],"products":[],"doctorGuidances":[]}
     */

    private int doctorRelationshipId;
    private int sourceDoctorId;
    private int destinationDoctorId;
    private String status;
    private Date createdDate;
    private Date modifiedDate;
    private String comment;
    private boolean isActive;

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * doctorId : 11807
     * avatar : http://kaurihealthrecordimagetest.kaurihealth.com/ad7c58aa9b3a40749bc57d58e8c1a3f6/Icon/67cd43774fb0436aafcb8c249637d23b.jpeg
     * email : null
     * workPhone : null
     * mobilePhone : null
     * isValidDoctor : false
     * accountActivateDate : null
     * doctorType : 普通医生
     * firstName : sc
     * lastName : wang
     * kauriHealthId : ad7c58aa9b3a40749bc57d58e8c1a3f6
     * gender : 男
     * dateOfBirth : 1989-06-23T08:37:20+08:00
     * userId : 33056
     * registPercentage : 100.0
     * years : null
     * practiceField :  kkkkkkkk
     * introduction : null
     * fullName : wangsc
     * totalCredit : 0.01
     * availableCredit : 0.0
     * allowUnknownMessage : true
     * educationTitle : 博士生导师
     * mentorshipTitle : 教授
     * hospitalTitle : 主任医师
     * nationalIdentity :
     * certificationNumber : 989884464566454
     * workingExperience :  Kkkllllllllllll
     * doctorInformations : {"doctorInformationId":635,"departmentId":40,"hospitalName":"天津市职工医院","doctorId":11807,"department":{"departmentId":40,"departmentName":"手显微外科","level":2,"parent":37},"outpatientAvalibility":null}
     * doctorEducation : null
     * doctorGrades : []
     * doctorCertifications : []
     * products : []
     * doctorGuidances : []
     */

    private RelatedDoctorBean relatedDoctor;

    public int getDoctorRelationshipId() {
        return doctorRelationshipId;
    }

    public void setDoctorRelationshipId(int doctorRelationshipId) {
        this.doctorRelationshipId = doctorRelationshipId;
    }

    public int getSourceDoctorId() {
        return sourceDoctorId;
    }

    public void setSourceDoctorId(int sourceDoctorId) {
        this.sourceDoctorId = sourceDoctorId;
    }

    public int getDestinationDoctorId() {
        return destinationDoctorId;
    }

    public void setDestinationDoctorId(int destinationDoctorId) {
        this.destinationDoctorId = destinationDoctorId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public RelatedDoctorBean getRelatedDoctor() {
        return relatedDoctor;
    }

    public void setRelatedDoctor(RelatedDoctorBean relatedDoctor) {
        this.relatedDoctor = relatedDoctor;
    }

}
