package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.request_bean.bean.DoctorProductPriceBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Nick on 24/08/2016.
 */
public interface IRemoteDoctorServiceSettingView extends IMvpView {
    //得到医生远程咨询服务的bean
    DoctorProductPriceBean getDoctorProductPriceBean();
}
