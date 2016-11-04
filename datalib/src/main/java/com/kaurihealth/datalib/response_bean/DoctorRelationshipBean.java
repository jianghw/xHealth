package com.kaurihealth.datalib.response_bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 张磊 on 2016/7/27.
 * 介绍：
 */
public class DoctorRelationshipBean implements Serializable{
    public int doctorRelationshipId;  //关联医生ID
    public int sourceDoctorId; //发起医生ID
    public int destinationDoctorId; //关联医生ID
    public String status;  //状态（0：等待。1：接受，2：拒绝）

    public Date createdDate; //创建时间
    public String comment; //说明
    public DoctorDisplayBean relatedDoctor;  //关联医生的参数
    public int type;//新添加的字段为了判断

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

    public DoctorDisplayBean getRelatedDoctor() {
        return relatedDoctor;
    }

    public void setRelatedDoctor(DoctorDisplayBean relatedDoctor) {
        this.relatedDoctor = relatedDoctor;
    }
}
