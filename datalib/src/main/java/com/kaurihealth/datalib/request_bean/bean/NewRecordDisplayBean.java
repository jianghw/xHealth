package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by 张磊 on 2016/7/16.
 * 介绍：
 */
public class NewRecordDisplayBean {
    public String recordType;
    public int ownerId;
    public NewOutpatientRecordDetailDisplayBean outpatientRecord;
    public NewRemoteConsultationRecordDetailDisplayBean remoteConsultationRecord;
    public NewOnlineConsultationRecordDetailDisplayBean onlineConsultationRecord;
}
