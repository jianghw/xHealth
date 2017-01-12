package com.kaurihealth.mvplib.save_record_p;

import com.kaurihealth.datalib.request_bean.bean.NewPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/9/19.
 * <p/>
 * Describe:
 */
public interface IOutpatientRecordSaveView extends IMvpView {
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
     * 添加 bean
     * @return
     */
    NewPatientRecordDisplayBean getNewPatientRecordDisplayBean();
}
