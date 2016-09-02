package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注：病例的参数
 */
public class RecordDisplayBean implements Serializable{
    /**
     * 病例ID
     */
    public int recordId;
    /**
     * 病例类型（0：门诊记录电子病历，1：远程医疗咨询，2：网络医疗咨询，4：入院记录，
     * 5：院内治疗相关记录，6：出院记录，7：协作医疗咨询，8：门诊记录图片存档）
     */
    public String recordType;
    /**
     * 是否已验证
     */
    public boolean isValid;
    /**
     * 用户ID
     */
    public int ownerId;
    /**
     * 患者病历ID
     */
    public int patientRecordId;
    /**
     * 门诊记录的参数
     */
    public OutpatientRecordDetailDisplayBean outpatientRecord;
    /**
     * 远程会诊记录的参数
     */
    public RemoteConsultationRecordDetailDisplayBean remoteConsultationRecord;
    /**
     * 在线咨询实录的参数
     */
    public OnlineConsultationRecordDetailDisplayBean onlineConsultationRecord;


}
