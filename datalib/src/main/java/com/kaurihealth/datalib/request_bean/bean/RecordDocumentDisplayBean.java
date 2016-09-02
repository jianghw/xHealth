package com.kaurihealth.datalib.request_bean.bean;

import java.io.Serializable;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/6/7.
 * 备注：
 */
public class RecordDocumentDisplayBean implements Serializable{
    /**
     *
     */
    public Integer recordDocumentId;
    /**
     *
     */
    public String documentUrl;
    /**
     *
     */
    public String documentFormat;
    /**
     *
     */
    public String fileName;
    /**
     *
     */
    public String displayName;
    /**
     *
     */
    public String comment;
    /**
     *
     */
    public Boolean isDeleted;
    /**
     *
     */
    public Integer patientRecordId;

}
