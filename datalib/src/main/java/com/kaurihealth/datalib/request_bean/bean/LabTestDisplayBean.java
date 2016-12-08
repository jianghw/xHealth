package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注：实验室测试的参数
 */
public class LabTestDisplayBean implements Serializable{
    /**
     * 实验室测试ID
     */
    public int labTestId;
    /**
     * 患者病历ID
     */
    public int patientRecordId;
    /**
     * 获取或设置实验室测试的类型
     */
    public String labTestType;
}
