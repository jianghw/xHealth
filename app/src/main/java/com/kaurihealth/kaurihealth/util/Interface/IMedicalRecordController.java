package com.kaurihealth.kaurihealth.util.Interface;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.RecordDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;

import java.util.List;

/**
 * Created by 张磊 on 2016/6/23.
 * 介绍：
 */
public interface IMedicalRecordController {
    /**
     * 修改类型
     */
    void editCategory(String category);

    String getCategory();

    /**
     * 修改项目
     */
    void editSubject(String subject);

    String getSubject();

    /**
     * 修改门诊时间
     */
    void editRecordDate(String RecordDate);

    String getRecordDate();

    /**
     * 修改机构
     */
    void editHospital(String Hospital);

    String getHospital();

    /**
     * 修改医师
     */
    void editDoctor(String Doctor);

    String getDoctor();

    /**
     * 添加图片
     */
    void addImage(RecordDocumentDisplayBean[] Image);

    List<RecordDocumentDisplayBean> getImages();

    /**
     * 删除图片
     */
    void deleteImage(RecordDocumentDisplayBean imag);

    /**
     * 修改备注
     */
    void editComment(String comment);

    String getComment();

    PatientRecordDisplayBean getBean();

    int getCreateBy();

    void setBean(PatientRecordDisplayBean bean);

    String getDepartmentName();

    void editDepartment(DepartmentDisplayBean department);
    int getPatientRecordId();
}
