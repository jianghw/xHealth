package com.kaurihealth.mvplib.patient_p.medical_records;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garnet_Wu on 2016/9/18.
 */
public interface IRemoteMedicalConsultationPresenter<V> extends IMvpPresenter<V> {

    //标记已经删除的图片, 改为true ,更新内部的bean
    void markDeleteImages(final ArrayList<String> paths, PatientRecordDisplayBean patientRecordDisplayBean, List<RecordDocumentsBean> list);

    //插入新的临床诊疗记录，医生和患者的tokentoken都可以
    void  addNewPatientRecord();
}

