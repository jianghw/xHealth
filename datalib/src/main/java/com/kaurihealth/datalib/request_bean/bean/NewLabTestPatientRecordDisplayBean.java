package com.kaurihealth.datalib.request_bean.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by Nick on 21/07/2016.
 */
public class NewLabTestPatientRecordDisplayBean {
    @JsonIgnore
    public static int errorDefault = -10;
    public Integer patientId;
    public String comment;
    public String recordDate;
    public String doctor;
    public String hospital;
    public Integer departmentId = errorDefault;
    public String subject;
    public NewLabTestDetailDisplayBean labTest;
    public String status;
    public List<NewDocumentDisplayBean> recordDocuments;
}
