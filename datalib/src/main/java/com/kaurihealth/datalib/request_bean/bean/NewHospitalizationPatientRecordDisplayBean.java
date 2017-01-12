package com.kaurihealth.datalib.request_bean.bean;

import com.kaurihealth.datalib.response_bean.HospitalizationBean;

import java.util.Date;
import java.util.List;

/**
 * Created by jianghw on 2017/1/9.
 * <p/>
 * Describe:插入新的住院记录 医生和患者的token都可以用
 * POST api/Hospitalization/InsertHospitalization
 */

public class NewHospitalizationPatientRecordDisplayBean {

    /**
     * patientId : 1
     * comment : sample string 2
     * recordDate : 2017-01-09T20:10:38+08:00
     * doctor : sample string 4
     * hospital : sample string 5
     * departmentId : 6
     * subject : sample string 7
     * hospitalization : [{"hospitalizationType":"sample string 1","createdDate":"2017-01-09T20:10:38+08:00","doctor":"sample string 3","hospital":"sample string 4","departmentId":5,"hospitalizationDocuments":[{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6},{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6}],"comment":"sample string 6","hospitalNumber":"sample string 7"},{"hospitalizationType":"sample string 1","createdDate":"2017-01-09T20:10:38+08:00","doctor":"sample string 3","hospital":"sample string 4","departmentId":5,"hospitalizationDocuments":[{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6},{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6}],"comment":"sample string 6","hospitalNumber":"sample string 7"}]
     * status : sample string 8
     */

    private int patientId;
    private String comment;
    private Date recordDate;
    private String doctor;
    private String hospital;
    private int departmentId;
    private String subject;
    private String status;
    /**
     * hospitalizationType : sample string 1
     * createdDate : 2017-01-09T20:10:38+08:00
     * doctor : sample string 3
     * hospital : sample string 4
     * departmentId : 5
     * hospitalizationDocuments : [{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6},{"fileName":"sample string 1","comment":"sample string 2","documentFormat":"sample string 3","documentUrl":"sample string 4","displayName":"sample string 5","hospitalizationId":6}]
     * comment : sample string 6
     * hospitalNumber : sample string 7
     */

    private List<HospitalizationBean> hospitalization;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HospitalizationBean> getHospitalization() {
        return hospitalization;
    }

    public void setHospitalization(List<HospitalizationBean> hospitalization) {
        this.hospitalization = hospitalization;
    }


}
