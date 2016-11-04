package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;

/**
 * Created by mip on 2016/9/9.
 */
public interface IEditPrescriptionView {

    PrescriptionBean getCurrentNewPrescriptionBean();

    void AddPrescriptionIsSuccessful(PrescriptionBean prescriptionBean);
}
