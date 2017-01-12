package com.kaurihealth.mvplib.mine_p;


import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by jianghw on 24/08/2016.
 */
public interface IDoctorCompileView extends IMvpView {
    DoctorDisplayBean getDoctorDisplayBean();

    void renderView();
}
