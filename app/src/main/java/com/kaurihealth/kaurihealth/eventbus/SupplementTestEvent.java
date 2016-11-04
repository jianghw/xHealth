package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;

import java.util.List;

/**
 * Created by mip on 2016/9/27.
 * 描述：给supplementTest传bean
 */
public class SupplementTestEvent {
    private final List<PatientRecordDisplayBean> mString;

    public SupplementTestEvent(List<PatientRecordDisplayBean> message) {
        this.mString = message;
    }

    public List<PatientRecordDisplayBean>getListsMessage() {
        return mString;
    }

}
