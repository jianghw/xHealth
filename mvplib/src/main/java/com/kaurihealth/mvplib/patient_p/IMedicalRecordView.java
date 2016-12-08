package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

public interface IMedicalRecordView extends IMvpView {
    /**
     * 刷新数据
     *
     * @param flag
     */
    void loadingIndicator(final boolean flag);

    int getPatientId();

    DoctorPatientRelationshipBean getShipBean();

    void callBackClinical(List<PatientRecordDisplayBean> beanList);

    void callBackLob(List<PatientRecordDisplayBean> beanList);

    void callBackAuxiliary(List<PatientRecordDisplayBean> beanList);

    void callBackPathology(List<PatientRecordDisplayBean> beanList);

    /**
     * 数据跳转
     */
    void sendEventBusByString(String category, String subject);
}
