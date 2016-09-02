package com.kaurihealth.kaurihealth.util.Abstract;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Interface.IOutPatient;

/**
 * Created by 张磊 on 2016/6/23.
 * 介绍：修改门诊记录病历
 */
public abstract class AbsMedicalRecordOutpatient extends AbsMedicalRecordCommon implements IOutPatient{
    public AbsMedicalRecordOutpatient(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }
}
