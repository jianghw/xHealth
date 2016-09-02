package com.kaurihealth.datalib.request_bean.bean;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nick on 24/04/2016.
 */
public class DoctorGuidanceDisplayBean implements Serializable{
    /// <summary>
    /// 指导医生ID
    /// </summary>
    public int doctorGuidanceId;

    /// <summary>
    /// 导师ID
    /// </summary>
    public int coachDoctorId;

    /// <summary>
    /// 学生ID
    /// </summary>
    public int studentDoctorId;

    /// <summary>
    /// 是否激活
    /// </summary>
    public boolean isActive;

    /// <summary>
    /// 指导类型（0：师生指导，1：跨院指导，2：学术指导，3:监督指导，4：临床指导，5：分析指导）
    /// </summary>
    public String guidanceType;

    /// <summary>
    /// 开始时间
    /// </summary>
    public Date startDate;

    /// <summary>
    /// 导师的参数
    /// </summary>
    public DoctorDisplayBean coachDoctor;

    /// <summary>
    /// 学生的参数
    /// </summary>
    public DoctorDisplayBean studentDoctor;
}
