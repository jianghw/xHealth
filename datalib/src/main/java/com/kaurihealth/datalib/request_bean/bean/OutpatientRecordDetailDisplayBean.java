package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注： 门诊记录的参数
 */
public class OutpatientRecordDetailDisplayBean implements Serializable {
    /**
     * 门诊记录Id
     */
    public int outPatientRecordId;
    /**
     * 主诉/现病史
     */
    public String presentIllness;
    /**
     * 是否已阅过去的疾病
     */
    public boolean isPastIllnessReviewed;
    /**
     * 是否更新已阅的疾病
     */
    public boolean isPastIllnessUpToDate;
    /**
     * Hr
     */
    public Double hrValue = null;
    /**
     * UpperBp
     */
    public Double upperBpValue = null;
    /**
     * LowerBp
     */
    public Double lowerBpValue = null;
    /**
     * Rr
     */
    public Double rrValue = null;
    /**
     * TV
     */
    public Double tValue = null;
    /**
     * 身体检查
     */
    public String physicalExamination;
    /**
     * 是否处理审查
     */
    public boolean isWorkupReviewed;
    /**
     * 是否审查最新的药物
     */
    public boolean isCurrentMedicationReviewed;
    /**
     * 是否已经更新最新的药物
     */
    public boolean isCurrentMedicationUpToDate;
    /**
     * 是否审查监测数据
     */
    public boolean isLongTermMonitoringDataReviewed;
    /**
     * 后处理审校意见(体格检查处理意见)
     */
    public String workupReviewComment;
    /**
     * 治疗计划
     */
    public String treatmentAndPlan;
    /**
     * 病例ID
     */
    public int recordId;
}
