package com.kaurihealth.mvplib.mine_p;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Nick on 29/08/2016.
 */
public interface ISelectTitleView extends IMvpView {
    DoctorDisplayBean getDoctorDisplayBean();
}
