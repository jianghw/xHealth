package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注：远程会诊记录的参数
 */
public class RemoteConsultationRecordDetailDisplayBean implements Serializable{
    /**
     * 远程医疗咨询病历ID
     */
    public int remoteConsultationRecordId;
    /**
     * 主诉/现病史
     */
    public String presentIllness;
    /**
     * 患者系统医疗记录是否已阅
     */
    public boolean isPatientHealthRecordReviewed;
    /**
     * 目前主要问题
     */
    public String currentHealthIssues;
    /**
     * 目前所接受治疗
     */
    public String currentTreatment;
    /**
     * 印象/咨询建议
     */
    public String remoteConsultationDoctorComment;
    /**
     * 病例ID
     */
    public int recordId;
}
