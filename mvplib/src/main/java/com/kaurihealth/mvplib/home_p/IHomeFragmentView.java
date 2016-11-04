package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by KauriHealth on 2016/9/19.
 */
public interface IHomeFragmentView extends IMvpView {

    void getDoctorRequestNumber(int number);

    void getPatientRequestNumber(int number);

    void getReferralPatientRequestNumber(int number);
}
