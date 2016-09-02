package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * Created by Nick on 24/04/2016.
 */
public class DepartmentDisplayBean implements Serializable{
    /// <summary>
    /// 科室ID
    /// </summary>
    public int departmentId;

    /// <summary>
    /// 科室名称
    /// </summary>
    public String departmentName;

    public int level;

    public Integer parent;
}
