package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Interface.ILabTestMedicalRecord;

/**
 * Created by 张磊 on 2016/6/28.
 * 介绍：
 */
public class MedicalRecordLabTest extends MedicalRecord implements ILabTestMedicalRecord {
    public MedicalRecordLabTest(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }

    @Override
    public void editLabTestType(String labTestType) {
        if (displayBean.labTest != null) {
            displayBean.labTest.labTestType = labTestType;
        }
    }

    @Override
    public String getLabTestType() {
        if (displayBean.labTest != null) {
            return displayBean.labTest.labTestType;
        }
        return null;
    }

}
