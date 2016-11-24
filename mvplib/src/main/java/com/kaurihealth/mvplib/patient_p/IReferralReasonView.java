package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralDoctorDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestReferralPatientDisplayBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by KauriHealth on 2016/10/25.
 */

public interface IReferralReasonView extends IMvpView{

    void insertPatientRequestReferralByDoctorListSucceed(ResponseDisplayBean displayBean);

    PatientRequestReferralDoctorDisplayBean getPatientRequestReferralDoctorDisplayBean();

    PatientRequestReferralPatientDisplayBean getPatientRequestBean();

}
