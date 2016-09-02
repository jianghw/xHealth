package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * Created by Nick on 21/04/2016.
 */
public class DoctorInformationDisplayBean implements Serializable{
    /// <summary>
    /// 医生信息ID
    /// </summary>
    public int doctorInformationId;

    /// <summary>
    /// 科室ID
    /// </summary>
    public int departmentId;

    /// <summary>
    /// 医院ID
    /// </summary>
    public int hospitalId;

    /// <summary>
    /// 医院名字
    /// </summary>
    public String hospitalName;

    /// <summary>
    /// 医生ID
    /// </summary>
    public int doctorId;

    /// <summary>
    /// 医院的参数
    /// </summary>

    //public HospitalDisplayBean hospital;


    /// <summary>
    /// 科室的参数
    /// </summary>
    public DepartmentDisplayBean department;

    /// <summary>
    /// 门诊作息
    /// </summary>
    public String outpatientAvalibility;
}
