package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by Nick on 1/09/2016.
 */
public interface  IDoctorTeamPresenter<V> extends IMvpPresenter<V> {

    //查询和自己已经建立联系的协作医生
    void  showCooperationDoctor(List<DoctorPatientRelationshipBean> beanList);

    void manualProcessingData(List<DoctorPatientRelationshipBean> relationshipBeen);

    void doctorProcessingData(List<DoctorPatientRelationshipBean> relationshipBeen, List<DoctorRelationshipBean> beanList);


}
