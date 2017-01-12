package com.kaurihealth.mvplib.save_record_p;

import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/12/30.
 */

public interface ILobCommonSaveView extends IMvpView{
    /**
     * 地址
     */
    List<String> getImagePathsList();

    /**
     * 请求bean
     */
    PatientRecordDisplayDto getRequestPatientRecordBean();

    void updatePatientRecordSucceed(PatientRecordDisplayDto bean);

    /**
     * 得到新的请求bean
     */
    NewLabTestPatientRecordDisplayBean getNewLabTestPatientRecordDisplayBean();
}
