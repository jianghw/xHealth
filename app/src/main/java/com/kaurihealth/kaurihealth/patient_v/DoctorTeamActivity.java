package com.kaurihealth.kaurihealth.patient_v;

import android.os.Bundle;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.patient_p.DoctorTeamPresenter;
import com.kaurihealth.mvplib.patient_p.IDoctorTeamView;

import javax.inject.Inject;


/**
 * 医疗团队
 */
public class DoctorTeamActivity extends BaseActivity  {



    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_doctor_team;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
