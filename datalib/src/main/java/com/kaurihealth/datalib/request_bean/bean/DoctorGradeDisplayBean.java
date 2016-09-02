package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 24/04/2016.
 */
public class DoctorGradeDisplayBean implements Serializable{
    /// <summary>
    /// 医生评分ID
    /// </summary>
    public int doctorGradeId;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 评分医生ID
    /// </summary>
    public int markDoctorId;

    /// <summary>
    /// 评分
    /// </summary>
    public int grade;

    /// <summary>
    /// 说明
    /// </summary>
    public String comment;

    /// <summary>
    /// 时间
    /// </summary>
    public Date date;
}
