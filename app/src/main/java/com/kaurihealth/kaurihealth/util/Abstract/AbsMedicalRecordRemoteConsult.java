package com.kaurihealth.kaurihealth.util.Abstract;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Interface.IRemote;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public abstract class AbsMedicalRecordRemoteConsult extends AbsMedicalRecordCommon implements IRemote {
    public AbsMedicalRecordRemoteConsult(PatientRecordDisplayBean displayBean) {
        super(displayBean);
    }
}
