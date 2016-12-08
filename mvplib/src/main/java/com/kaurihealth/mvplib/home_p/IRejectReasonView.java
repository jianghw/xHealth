package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDecisionBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by mip on 2016/9/21.
 */
public interface IRejectReasonView extends IMvpView{

    //得到当前的bean
    PatientRequestDecisionBean getCurrentPatientRequestDecisionBean();
    //得到响应结果
    void getRequestResult(ResponseDisplayBean bean);
}
