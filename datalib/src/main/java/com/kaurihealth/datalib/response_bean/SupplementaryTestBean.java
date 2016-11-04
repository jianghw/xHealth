package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/9/8.
 * <p>
 * 描述：
 * 补充测试的参数
 * {@link PatientRecordDisplayBean}
 */
public class SupplementaryTestBean {   //SupplementaryTestDisplayDto
    private int supplementaryTestId;  //补充测试ID
    private int patientRecordId;  //患者记录ID

    public int getSupplementaryTestId() {
        return supplementaryTestId;
    }

    public void setSupplementaryTestId(int supplementaryTestId) {
        this.supplementaryTestId = supplementaryTestId;
    }

    public int getPatientRecordId() {
        return patientRecordId;
    }

    public void setPatientRecordId(int patientRecordId) {
        this.patientRecordId = patientRecordId;
    }
}
