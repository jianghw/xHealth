package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.request_bean.bean.PrescriptionBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Nick on 1/09/2016.
 */
public interface IPrescriptionView extends IMvpView {

    int getPatientId();

    void getPresctiptionBeanList(List<PrescriptionBean> prescriptionBeen);

    void getDoctorRelationshipBean(DoctorPatientRelationshipBean relationshipBean);

    void loadingIndicator(boolean flag);
}
