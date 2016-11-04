package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.request_bean.bean.HealthConditionDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Nick on 1/09/2016.
 */
public interface IHealthConditionView extends IMvpView {

    int getCurrentPatientId();

    void getHealthCondition(List<HealthConditionDisplayBean> healthConditionDisplayBeen);

    HealthConditionDisplayBean[] getCurrentUpdataArray();

    Integer[] getCurrentDeleteArray();

    void getRequestReponse();
}
