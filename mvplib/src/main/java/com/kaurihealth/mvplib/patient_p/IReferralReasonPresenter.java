package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.ArrayList;

/**
 * Created by KauriHealth on 2016/10/25.
 */

public interface IReferralReasonPresenter<V> extends IMvpPresenter<V>{

    void createGroupChat(ArrayList<DoctorRelationshipBean> list);
}
