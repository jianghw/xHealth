package com.kaurihealth.kaurihealth.welcome_v;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.login_v.LoginActivity;
import com.kaurihealth.mvplib.welcome_p.IWelcomeView;
import com.kaurihealth.mvplib.welcome_p.WelcomePresenter;

import javax.inject.Inject;


/**
 * 欢迎
 */
public class WelcomeActivity extends BaseActivity implements IWelcomeView {

    @Inject
    WelcomePresenter<IWelcomeView> mPresenter;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        whetherAutomaticLogin();
    }

    /**
     * 是否自动登陆
     */
    private void whetherAutomaticLogin() {
        //TODO FIX
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void initDelayedView() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
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
