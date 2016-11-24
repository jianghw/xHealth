package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBeanWrapper;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Nick on 22/09/2016.
 */
public interface IFamilyMembersView extends IMvpView {

    //请求成功,数据通过View层显示
    void  lazyLoadingDataSuccess(List<FamilyMemberBeanWrapper> wrappers);

    //get patientId
    int  getPatientId();


    void loadingIndicator(boolean flag);

    //点击 "添加患者" 成功/ 失败
    void insertPatientSucceed(DoctorPatientRelationshipBean bean);
}
