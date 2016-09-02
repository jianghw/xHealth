package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.RecordDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Interface.IMedicalRecordController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：修改一般的医疗记录
 */
public class MedicalRecord implements IMedicalRecordController {
    public PatientRecordDisplayBean displayBean;

    public MedicalRecord(PatientRecordDisplayBean displayBean) {
        this.displayBean = displayBean;
    }

    @Override
    public void editCategory(String category) {
        displayBean.category = category;
    }

    @Override
    public String getCategory() {
        return displayBean.category;
    }

    @Override
    public void editSubject(String subject) {
        displayBean.subject = subject;
        if (displayBean.record != null) {
            displayBean.record.recordType = subject;
        }
    }

    @Override
    public String getSubject() {
        return displayBean.subject;
    }

    @Override
    public void editRecordDate(String RecordDate) {
        displayBean.recordDate = RecordDate;
    }

    @Override
    public String getRecordDate() {
        return displayBean.recordDate;
    }

    @Override
    public void editHospital(String Hospital) {
        displayBean.hospital = Hospital;
    }

    @Override
    public String getHospital() {
        return displayBean.hospital;
    }

    @Override
    public void editDoctor(String Doctor) {
        displayBean.doctor = Doctor;
    }

    @Override
    public String getDoctor() {
        return displayBean.doctor;
    }

    @Override
    public void addImage(RecordDocumentDisplayBean[] Image) {
        if (displayBean.recordDocuments == null) {
            displayBean.recordDocuments = new ArrayList<>();
        }
        displayBean.recordDocuments.addAll(Arrays.asList(Image));
    }

    @Override
    public List<RecordDocumentDisplayBean> getImages() {
        return displayBean.recordDocuments;
    }

    @Override
    public void deleteImage(RecordDocumentDisplayBean imag) {
        if (displayBean.recordDocuments != null) {
            for (int i = 0; i < displayBean.recordDocuments.size(); i++) {
                if (displayBean.recordDocuments.get(i).recordDocumentId == imag.recordDocumentId) {
                    displayBean.recordDocuments.get(i).isDeleted = true;
                }
            }
        }
    }

    @Override
    public void editComment(String comment) {
        displayBean.comment = comment;
    }

    @Override
    public String getComment() {
        return displayBean.comment;
    }

    @Override
    public PatientRecordDisplayBean getBean() {
        return displayBean;
    }

    @Override
    public int getCreateBy() {
        return displayBean.createdBy;
    }

    @Override
    public void setBean(PatientRecordDisplayBean bean) {
        this.displayBean = bean;
    }

    @Override
    public String getDepartmentName() {
        if (displayBean.department != null) {
            return displayBean.department.departmentName;
        }
        return null;
    }

    @Override
    public void editDepartment(DepartmentDisplayBean department) {
        displayBean.department=department;
        displayBean.departmentId=department.departmentId;
    }

    @Override
    public int getPatientRecordId() {
        return displayBean.patientRecordId;
    }
}
