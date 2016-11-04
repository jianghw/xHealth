package com.kaurihealth.datalib.request_bean.bean;

import java.util.Date;
import java.util.List;

/**
 * Created by Nick on 21/07/2016.
 * 插入新的临床诊疗记录 医生和患者的token都可以
 */
public class NewPatientRecordDisplayBean {
    public int patientId;  //患者ID
    public String comment;  //留言
    public Date recordDate; // 记录日期
    public String doctor;  //医生
    public String hospital;  // 医院
    public int departmentId; //科室ID
    public String subject; //主题
    public String status; //状态（0：草稿，1：发布）
    public NewRecordDisplayBean record;
    public List<NewDocumentDisplayBean> recordDocuments;  //病例图像的参数

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NewRecordDisplayBean getRecord() {
        return record;
    }

    public void setRecord(NewRecordDisplayBean record) {
        this.record = record;
    }

    public List<NewDocumentDisplayBean> getRecordDocuments() {
        return recordDocuments;
    }

    public void setRecordDocuments(List<NewDocumentDisplayBean> recordDocuments) {
        this.recordDocuments = recordDocuments;
    }
}
