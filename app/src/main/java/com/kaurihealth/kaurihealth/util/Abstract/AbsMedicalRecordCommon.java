package com.kaurihealth.kaurihealth.util.Abstract;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Interface.IMedicalRecordControllerCommon;
import com.kaurihealth.kaurihealth.util.MedicalRecord;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public abstract class AbsMedicalRecordCommon extends MedicalRecord implements IMedicalRecordControllerCommon {
    public AbsMedicalRecordCommon(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }
}
