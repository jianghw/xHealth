package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * Created by Nick on 24/04/2016.
 */
public class DoctorEducationDisplayBean implements Serializable{
    /// <summary>
    /// 医生学历ID
    /// </summary>
    public int doctorEducationId;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 教育程度
    /// </summary>
    public String educationLevel;

    /// <summary>
    /// 教育头衔
    /// </summary>
    public String educationTitle;

    /// <summary>
    /// 教育史
    /// </summary>
    public String educationHistory;

    /// <summary>
    /// 教育责任
    /// </summary>
    public String educationResponsibility;

    /// <summary>
    /// 研究方向
    /// </summary>
    public String researchDirection;

    /// <summary>
    /// 论文
    /// </summary>
    public String thesis;

    /// <summary>
    /// 奖项
    /// </summary>
    public String reward;
}
