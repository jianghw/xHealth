package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/9/8.
 * <p>
 * 描述：
 * 病理的参数
 * {@link PatientRecordDisplayBean}
 */
public class PathologyRecordBean {
    private int pathologyRecordId;
    private int patientRecordId;

    public int getPathologyRecordId() {
        return pathologyRecordId;
    }

    public void setPathologyRecordId(int pathologyRecordId) {
        this.pathologyRecordId = pathologyRecordId;
    }

    public int getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(int patientRecordId) {
        this.patientRecordId = patientRecordId;
    }
}
