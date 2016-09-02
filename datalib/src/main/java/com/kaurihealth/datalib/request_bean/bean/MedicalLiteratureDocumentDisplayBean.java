package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/31.
 * 备注：临床支持附件
 */
public class MedicalLiteratureDocumentDisplayBean implements Serializable {
    /**
     * 处方图像ID
     */
    public int medicalLiteratureDocumentId;
    /**
     * 处方ID
     */
    public int medicalLiteratureId;
    /**
     * 图像路径
     */
    public String documentUrl;
    /**
     * 图像格式
     */
    public String documentFormat;
    /**
     * 文件名
     */
    public String fileName;
    /**
     * 说明
     */
    public String comment;
    /**
     * 是否删除
     */
    public boolean isDeleted;
    /**
     * 显示名称
     */
    public String displayName;
}
