package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/10/26.
 */

public interface IReferralPatientView extends IMvpView{

    void loadDoctorMainPageSucceed(List<MainPagePatientDisplayBean> list);

    void loadingIndicator(boolean flag);
}
