package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/10/24.
 */

public interface IReferralDoctorView extends IMvpView{

    void getDoctorRelationshipBeanList(List<DoctorRelationshipBean> doctorRelationshipBeen);

}
