package com.kaurihealth.mvplib.save_record_p;

import com.kaurihealth.datalib.request_bean.bean.NewHospitalizationPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2017/1/5.
 */

public interface IHospitalRecordView extends IMvpView {
    /**
     * 地址
     */
    List<String> getImagePathsList();

    /**
     * 入院图片
     */
    List<String> getAdmissionImagePathsList();

    /**
     * 院内
     */
    List<String> getCourtImagePathsList();

    /**
     * 出院
     */
    List<String> getOutImagePathsList();

    /**
     * 入院请求bean
     */
    PatientRecordDisplayDto getAdmissionRequestPatientRecordBean();

    /**
     * 院内请求bean
     */
    PatientRecordDisplayDto getCourtRequestPatientRecordBean();

    /**
     * 出院请求bean
     */
    PatientRecordDisplayDto getOutRequestPatientRecordBean();

    void updatePatientRecordSucceed(PatientRecordDisplayDto bean);

    NewHospitalizationPatientRecordDisplayBean getAdmissionInsertRecordBean();

    NewHospitalizationPatientRecordDisplayBean getCourtInsertRecordBean();

    NewHospitalizationPatientRecordDisplayBean getOutInsertRecordBean();
}
