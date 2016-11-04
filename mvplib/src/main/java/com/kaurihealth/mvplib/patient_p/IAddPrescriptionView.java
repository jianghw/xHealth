package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.request_bean.bean.NewPrescriptionBean;
import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Mip on 2016/9/6.
 */
public interface IAddPrescriptionView extends IMvpView{

    NewPrescriptionBean getCurrentNewPrescriptionBean();

    void AddPrescriptionIsSuccessful(PrescriptionBean prescriptionBean);
}
