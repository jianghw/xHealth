package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by Nick on 1/09/2016.
 */
public interface IDoctorTeamView extends IMvpView {
    //医患id   -->   LoadDoctorPatientRelationshipForPatientId(int patientId)
    int getPatientRelationshipId();

    void lazyLoadingDataSuccess(List<?> list);
}
