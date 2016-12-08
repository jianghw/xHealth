package com.kaurihealth.kaurihealth.welcome_v;

import android.os.Bundle;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.datalib.response_bean.TokenBean;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.login_v.LoginActivity;
import com.kaurihealth.kaurihealth.main_v.MainActivity;
import com.kaurihealth.kaurihealth.register_v.RegisterPersonInfoActivity;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.welcome_p.IWelcomeView;
import com.kaurihealth.mvplib.welcome_p.WelcomePresenter;
import com.kaurihealth.utilslib.constant.Global;

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
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
    }

    @Override
    protected void initDelayedData() {
        whetherAutomaticLogin();
    }

    /**
     * 是否自动登陆
     * 根据本地数据做依据
     */
    private void whetherAutomaticLogin() {
        mPresenter.onSubscribe();
    }

    /**
     * 禁止回退
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        return keyCode == KeyEvent.KEYCODE_BACK || super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unSubscribe();
    }

    /**
     * ----------------------------继承基础mvpView方法-----------------------------------
     */
    @Override
    public void switchPageUI(String className) {
        switch (className) {
            case Global.Jump.LoginActivity:
                skipTo(LoginActivity.class);
                break;
            case Global.Jump.MainActivity:
                skipTo(MainActivity.class);
                break;
            case Global.Jump.RegisterPersonInfoActivity:
                skipTo(RegisterPersonInfoActivity.class);
                break;
            default:
                break;
        }
        finish();
    }

    @Override
    public void completeRegister() {
        switchPageUI(Global.Jump.RegisterPersonInfoActivity);
    }

    @Override
    public void initChatKitOpen(TokenBean bean) {
        UserBean userBean = bean.getUser();
        if (userBean != null) {
            LCChatKit.getInstance().open(userBean.getKauriHealthId(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                  switchPageUI(Global.Jump.MainActivity);
                }
            });
        }
    }
}
