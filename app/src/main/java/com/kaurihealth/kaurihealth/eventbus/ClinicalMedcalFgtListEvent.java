package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

import java.util.List;

/**
 * Created by jianghw on 2016/8/24.
 * <p>
 * 描述：传 List<PatientRecordDisplayBean> 给  ClinicalMedicalFragment
 */
public class ClinicalMedcalFgtListEvent {

    private final List<PatientRecordDisplayBean> mString;

    public ClinicalMedcalFgtListEvent(List<PatientRecordDisplayBean>message) {
        this.mString = message;
    }

    public List<PatientRecordDisplayBean>getListsMessage() {
        return mString;
    }
}
