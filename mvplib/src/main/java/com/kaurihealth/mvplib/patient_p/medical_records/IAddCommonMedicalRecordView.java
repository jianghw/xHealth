package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.datalib.request_bean.bean.NewPathologyPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewSupplementaryTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.ArrayList;

/**
 * Created by mip on 2016/9/27.
 */
public interface IAddCommonMedicalRecordView extends IMvpView{
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
     * 添加新的辅助检查 bean
     * @return
     */
    NewSupplementaryTestPatientRecordDisplayBean getNewSupplementaryTestPatientRecordDisplayBean();

    /**
     * 添加新的病理bean
     * @return
     */
    NewPathologyPatientRecordDisplayBean getNewPathologyPatientRecordDisplayBean();

    /**
     * 成功回调
     * @param patientRecordDisplayBean
     */
    void updateSucceed(PatientRecordDisplayBean patientRecordDisplayBean);

    DoctorPatientRelationshipBean getDoctorPatientRelationshipBean();

    int  getDoctorPatientId();

    void finishPage();
}
