package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.view.View;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.mvplib.mine_p.IServiceSettingView;
import com.kaurihealth.mvplib.mine_p.ServiceSettingPresenter;

import javax.inject.Inject;

import butterknife.OnClick;


/**
 * Created By Garnet_Wu
 * 描述: 我的 --> 服务项目
 */
public class ServiceSettingActivity extends BaseActivity implements IServiceSettingView {

    @Inject
    ServiceSettingPresenter<IServiceSettingView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_service_setting;
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

    @OnClick({R.id.rlayExclusiveDoctor, R.id.rlayRemoteConsultSetting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlayExclusiveDoctor:
                skipTo(PersonalDoctorServiceSettingActivity.class);
                break;
            case R.id.rlayRemoteConsultSetting:
                skipTo(RemoteDoctorServiceSettingActivity.class);
                break;
        }
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     *
     * @param className*/

    @Override
    public void switchPageUI(String className) {

    }

}
