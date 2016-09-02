package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;
import com.kaurihealth.datalib.request_bean.bean.PriceDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Nick on 24/08/2016.
 */
public interface IPersonalDoctorServiceSettingView extends IMvpView {
    //造专属医生的Bean
    PriceDisplayBean getPriceDisplayBean();

    NewPriceBean createNewPriceBean();
}
