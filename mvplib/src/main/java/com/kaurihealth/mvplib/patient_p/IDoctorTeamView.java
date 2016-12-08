package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.RelationshipBeanWrapper;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Nick on 1/09/2016.
 */
public interface IDoctorTeamView extends IMvpView {

    int getPatientId();

    void lazyLoadingDataSuccess(List<RelationshipBeanWrapper> list);

    void loadingIndicator(boolean flag);
}
