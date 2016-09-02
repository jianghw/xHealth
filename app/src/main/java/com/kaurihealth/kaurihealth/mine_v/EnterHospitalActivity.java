package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.EnterHospitalPresenter;
import com.kaurihealth.mvplib.mine_p.IEnterHospitalView;

import javax.inject.Inject;


/**
 * 我的Button 里面的设置   created by Nick
 */
public class EnterHospitalActivity extends BaseActivity implements IEnterHospitalView {

    @Inject
    EnterHospitalPresenter<IEnterHospitalView> mPresenter;


    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_enter_hospital;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedView() {
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

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className*/

    @Override
    public void switchPageUI(String className) {

    }
}
