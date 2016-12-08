package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.ArrayList;

/**
 * Created by mip on 2016/9/29.
 */
public interface IAddAndEditLobTestView extends IMvpView{

    /**
     * 提交bean
     *
     * @return
     */
    PatientRecordDisplayBean getRequestPatientRecordBean();

    /**
     * 原始bean
     *
     * @return
     */
    PatientRecordDisplayBean getPatientRecordBean();

    /**
     * 操作后的路径
     *
     * @return
     */
    ArrayList<String> getPathsList();

    String getKauriHealthId();

    /**
     * 得到新的请求bean
     */
    NewLabTestPatientRecordDisplayBean getNewLabTestPatientRecordDisplayBean();

    /**
     * 成功回调
     * @param patientRecordDisplayBean
     */
    void updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean);

    DoctorPatientRelationshipBean getDoctorPatientRelationshipBean();
    int getDoctorPatientId();

    void finishPage();
}
