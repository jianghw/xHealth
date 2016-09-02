package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * Created by Nick on 24/04/2016.
 */
public class DoctorCertificationDisplayBean implements Serializable{
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
}
