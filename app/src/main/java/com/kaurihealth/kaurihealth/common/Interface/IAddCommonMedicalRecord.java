package com.kaurihealth.kaurihealth.common.Interface;

import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.common.bean.CommonMedicalBean;
import com.kaurihealth.kaurihealth.common.bean.MesBean;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by 张磊 on 2016/7/16.
 * 介绍：
 */
public interface IAddCommonMedicalRecord {

    void setListener(SuccessInterfaceM<MesBean> listener);

    CommonMedicalBean createCommonMedicalBean();

    CommonMedicalBean getCommonMedicalBean();

    boolean verifyData(CommonMedicalBean bean);

    boolean judgeMeedToUpload(CommonMedicalBean bean);

    void uploadImags(CommonMedicalBean bean, SuccessInterfaceM<String> uploadSuccessListener);

    void addMedicalRecord(CommonMedicalBean bean);

    String getCategory();

    int getPatientId();

    //病理
    void insertPathologyRecord(CommonMedicalBean bean);

    //临床诊疗
    void addNewPatientRecord(CommonMedicalBean bean);

    //辅助检查
    void insertSupplementaryTest(CommonMedicalBean bean);


    void onFailure(Call<PatientRecordDisplayBean> call, Throwable t, SuccessInterfaceM<MesBean> listener);

    void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response, SuccessInterfaceM<MesBean> listener);
}
