package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.request_bean.bean.NewReferralMessageAlertBean;
import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface IAddVisitRecordView extends IMvpView {

    NewReferralMessageAlertBean getNewReferralMessageAlertBean();

    void insertHealthConditionSucceed(ReferralMessageAlertDisplayBean bean);
}
