package com.kaurihealth.mvplib.casehistory_p;

import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：
 */
public interface IAllMedicalRecordsPresenter<V> extends IMvpPresenter<V> {

    void loadingRemoteData(boolean isDirty);

    void loadDoctorMainPage();

    void manualSearch(CharSequence text, List<MainPagePatientDisplayBean> list);
}
