package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注：
 */
public class OnlineConsultationRecordDetailDisplayBean implements Serializable {
    /**
     * 网络医疗咨询病历ID
     */
    public int onlineConsultationRecordId;
    /**
     * 网络咨询原因
     */
    public String onlineConsultationPurpose;
    /**
     * 目前主要问题
     */
    public String majorIssue;
    /**
     * 印象/咨询建议
     */
    public String onlineConsultationDoctorComment;
    /**
     * 病例ID
     */
    public int recordId;
    /**
     * 目前所接受治疗
     */
    public String currentTreatment;
    /**
     * 主诉/现病史
     */
    public String presentIllness;
}
