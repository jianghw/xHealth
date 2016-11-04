package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by 张磊 on 2016/7/16.
 * 介绍：插入新的临床诊疗记录--病例的参数
 */
public class NewRecordDisplayBean {
    /**
     * 病例类型（0：门诊记录电子病历，1：远程医疗咨询，2：网络医疗咨询，4：入院记录，5：院内治疗相关记录，6：出院记录，7：协作医疗咨询，8：门诊记录图片存档）
     */
    public String recordType;  //病例类型
    public int ownerId;  //用户ID/病人ID
    public NewOutpatientRecordDetailDisplayBean outpatientRecord;  //门诊记录的参数
    public NewRemoteConsultationRecordDetailDisplayBean remoteConsultationRecord; //远程会诊记录的参数
    public NewOnlineConsultationRecordDetailDisplayBean onlineConsultationRecord; //在线咨询实录的参数

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public NewOutpatientRecordDetailDisplayBean getOutpatientRecord() {
        return outpatientRecord;
    }

    public void setOutpatientRecord(NewOutpatientRecordDetailDisplayBean outpatientRecord) {
        this.outpatientRecord = outpatientRecord;
    }

    public NewRemoteConsultationRecordDetailDisplayBean getRemoteConsultationRecord() {
        return remoteConsultationRecord;
    }

    public void setRemoteConsultationRecord(NewRemoteConsultationRecordDetailDisplayBean remoteConsultationRecord) {
        this.remoteConsultationRecord = remoteConsultationRecord;
    }

    public NewOnlineConsultationRecordDetailDisplayBean getOnlineConsultationRecord() {
        return onlineConsultationRecord;
    }

    public void setOnlineConsultationRecord(NewOnlineConsultationRecordDetailDisplayBean onlineConsultationRecord) {
        this.onlineConsultationRecord = onlineConsultationRecord;
    }
}
