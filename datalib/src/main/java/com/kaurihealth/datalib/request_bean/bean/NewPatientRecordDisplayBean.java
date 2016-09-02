package com.kaurihealth.datalib.request_bean.bean;

import java.util.List;

/**
 * Created by Nick on 21/07/2016.
 */
public class NewPatientRecordDisplayBean {
    public int patientId;
    public String comment;
    public String recordDate;
    public String doctor;
    public String hospital;
    public int departmentId;
    public String subject;
    public String status;
    public 	NewRecordDisplayBean record;
    public List<NewDocumentDisplayBean> recordDocuments;
}
