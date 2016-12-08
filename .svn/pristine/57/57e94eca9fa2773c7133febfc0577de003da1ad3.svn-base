package com.kaurihealth.datalib.response_bean;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/**
 * Created by Nick on 24/04/2016.
  医生证书信息的参数   {@link RelatedDoctorBean}
 */
public class DoctorCertificationDisplayBean implements Serializable{
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    /// <summary>
    /// 医生文件ID
    /// </summary>
    public int doctorDocumentId;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 医生头像类型
    /// </summary>
    public int documentType;

    /// <summary>
    /// 头像路径
    /// </summary>
    public String documentUrl;

    /// <summary>
    /// 图像格式
    /// </summary>
    public String documentFormat;

    /// <summary>
    /// 文件名
    /// </summary>
    public String fileName;

    /// <summary>
    /// 说明
    /// </summary>
    public String comment;

    /// <summary>
    /// 显示名称
    /// </summary>
    public String displayName;

    public int getDoctorDocumentId() {
        return doctorDocumentId;
    }

    public void setDoctorDocumentId(int doctorDocumentId) {
        this.doctorDocumentId = doctorDocumentId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getDocumentType() {
        return documentType;
    }

    public void setDocumentType(int documentType) {
        this.documentType = documentType;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getDocumentFormat() {
        return documentFormat;
    }

    public void setDocumentFormat(String documentFormat) {
        this.documentFormat = documentFormat;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
