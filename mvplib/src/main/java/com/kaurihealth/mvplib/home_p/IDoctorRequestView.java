package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.datalib.response_bean.ResponseDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/9/19.
 */
public interface IDoctorRequestView extends IMvpView{

    void  getRequestResult(List<DoctorRelationshipBean> beanList);

    void loadingIndicator(boolean flag);

    void acceptDoctorRelationSuccess(ResponseDisplayBean bean);

    void rejectDoctorRelationSuccess(ResponseDisplayBean bean);

    void manualSearchSucceed(List<DoctorRelationshipBean> list);

    void removeNonAcceptedSuccess(ResponseDisplayBean bean);
}
