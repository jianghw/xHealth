package com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface;

import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;

import retrofit2.Callback;


/**
 * Created by 张磊 on 2016/6/13.
 * 介绍：
 */
public interface IMedicalRecordCommon {
    boolean requestEndDoctorPatientRelationship(DoctorPatientRelationshipBean bean, Callback<ResponseDisplayBean> callback);
}
