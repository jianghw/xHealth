package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;
import com.kaurihealth.utilslib.date.RemainTimeBean;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 * 描述：
 */
public interface IVerifiacationView extends IMvpView {

    int getPatientId();

    void send();
}
