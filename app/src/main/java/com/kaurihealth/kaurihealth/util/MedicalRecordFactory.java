package com.kaurihealth.kaurihealth.util;

import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordOnlineConsult;
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordOutpatient;
import com.kaurihealth.kaurihealth.util.Abstract.AbsMedicalRecordRemoteConsult;
import com.kaurihealth.kaurihealth.util.Interface.IMedicalRecordController;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public class MedicalRecordFactory {
    /**
     * 门诊记录电子病历编辑器
     */
    public static AbsMedicalRecordOutpatient createOutpatientEditor(PatientRecordDisplayBean patientRecordDisplayBean) {
        return new MedicalRecordOutpatient(patientRecordDisplayBean);
    }

    /**
     * 远程医疗咨询编辑器
     */
    public static AbsMedicalRecordRemoteConsult createRemoteEditor(PatientRecordDisplayBean patientRecordDisplayBean) {
        return new MedicalRecordRemoteConsult(patientRecordDisplayBean);
    }

    /**
     * 网络医疗咨询编辑器
     */
    public static AbsMedicalRecordOnlineConsult createOnlineEditor(PatientRecordDisplayBean patientRecordDisplayBean) {
        return new MedicalRecordOnlineConsult(patientRecordDisplayBean);
    }

    /**
     * 门诊图片记录存档  入院记录   院内治疗相关记录   出院记录 编辑器
     */
    public static IMedicalRecordController createClinicCommonEditor(PatientRecordDisplayBean patientRecordDisplayBean) {
        return new MedicalRecord(patientRecordDisplayBean);
    }

    /**
     * 辅助检查编辑器
     */
    public static IMedicalRecordController createSupplementaryEditor(PatientRecordDisplayBean patientRecordDisplayBean) {
        return new MedicalRecord(patientRecordDisplayBean);
    }

    /**
     * 实验室检查编辑器
     */
    public static MedicalRecordLabTest createLabEditor(PatientRecordDisplayBean patientRecordDisplayBean) {
        return new MedicalRecordLabTest(patientRecordDisplayBean);
    }

    /**
     * 病理检查编辑器
     */
    public static IMedicalRecordController createPathology(PatientRecordDisplayBean patientRecordDisplayBean) {
        return new MedicalRecord(patientRecordDisplayBean);
    }

}
