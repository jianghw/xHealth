package com.kaurihealth.mvplib.patient_new;

import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchPatientBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/12/15.
 */

public interface IPatientSearchView extends IMvpView{

    InitialiseSearchRequestBean getCurrentSearchRequestBean();

    void updataDataSucceed(List<SearchPatientBean> been);

    int getDoctorId();

    void getResult(DoctorRelationshipBean relationshipBean, boolean b);

    void insertPatientSucceed(DoctorPatientRelationshipBean bean);

    void loadingIndicator(boolean flag);
}
