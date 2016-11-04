package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/10/26.
 */

public interface IReferralPatientView extends IMvpView{

    void getResultBean(List<DoctorPatientRelationshipBean> bean);

}
