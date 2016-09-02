package com.kaurihealth.kaurihealth.patientwithdoctor.util;

import android.content.Context;

import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.kaurihealth.patientwithdoctor.util.Interface.IMedicalRecordCommon;
import com.kaurihealth.kaurihealth.services.IServiceFactory;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Url;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by 张磊 on 2016/6/13.
 * 介绍：
 */
public class MedicalRecordCommon implements IMedicalRecordCommon {

    private final IDoctorPatientRelationshipService doctorPatientRelationshipService;

    public MedicalRecordCommon(Context context) {
        IServiceFactory iServiceFactory = new ServiceFactory(Url.prefix,context);
        doctorPatientRelationshipService = iServiceFactory.getDoctorPatientRelationshipService();
    }

    @Override
    public boolean requestEndDoctorPatientRelationship(DoctorPatientRelationshipBean relationshipDto, Callback<ResponseDisplayBean> callback) {
        if (relationshipDto.relationship.equals("普通") && !relationshipDto.relationshipReason.equals("门诊")) {
            Call<ResponseDisplayBean> responseDisplayBeanCall = doctorPatientRelationshipService.RequestEndDoctorPatientRelationship(relationshipDto.doctorPatientId);
            responseDisplayBeanCall.enqueue(callback);
            return true;
        } else {
            return false;
        }
    }
}
