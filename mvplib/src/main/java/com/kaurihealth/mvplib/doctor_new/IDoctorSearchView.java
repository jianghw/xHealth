package com.kaurihealth.mvplib.doctor_new;

import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/12/15.
 */

public interface IDoctorSearchView extends IMvpView{

    InitialiseSearchRequestBean getCurrentSearchRequestBean();

    void updataDataSucceed(List<SearchBooleanResultBean> been);

    int getDoctorId();

    void getResult(DoctorRelationshipBean relationshipBean, boolean b);
}
