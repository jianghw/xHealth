package com.kaurihealth.mvplib.casehistory_p;


import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface IAllMedicalRecordsView extends IMvpView {

    void loadingIndicator(boolean flag);

    void loadDoctorMainPageSucceed(List<MainPagePatientDisplayBean> list);

    void manualSearchSucceed(List<MainPagePatientDisplayBean> list);
}
