package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * Created by 张磊 on 2016/7/16.
 * 介绍：
 */
public class NewSupplementaryTestPatientRecordDisplayBean {
    public String comment;
    public int departmentId;
    public String doctor;
    public String hospital;
    public int patientId;
    public String recordDate;
    public List<NewDocumentDisplayBean> recordDocuments;
    public String subject;
    public String status;
}
