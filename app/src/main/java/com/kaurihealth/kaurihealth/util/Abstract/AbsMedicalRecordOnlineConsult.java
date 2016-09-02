package com.kaurihealth.kaurihealth.util.Abstract;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Interface.IOnlineConsult;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public abstract class AbsMedicalRecordOnlineConsult extends AbsMedicalRecordCommon implements IOnlineConsult{
    public AbsMedicalRecordOnlineConsult(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }
}
