package com.kaurihealth.kaurihealth;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.avos.avoscloud.AVOSCloud;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.cache.LeanchatUserProvider;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.kaurihealth.dagger.component.DaggerRepositoryComponent;
import com.kaurihealth.kaurihealth.dagger.component.RepositoryComponent;
import com.kaurihealth.kaurihealth.dagger.module.ApplicationModule;
import com.kaurihealth.kaurihealth.dagger.module.RepositoryModule;
import com.kaurihealth.kaurihealth.welcome_v.WelcomeActivity;
import com.kaurihealth.utilslib.SharedUtils;
import com.kaurihealth.utilslib.bugtag.BugTagUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;


/**
 * 修订日期:
 */
public class MyApplication extends Application {

    private RepositoryComponent mRepositoryComponent;
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);
//环境
        initEnvironmentMode();
//图片加载
        initImageLoad();
//dagger
        mRepositoryComponent = DaggerRepositoryComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext()))
                .repositoryModule(new RepositoryModule())
                .build();
        application = this;
    }

    public RepositoryComponent getComponent() {
        return mRepositoryComponent;
    }

    public static MyApplication getApp() {
        return application;
    }


    private void initImageLoad() {

    }

    private void initEnvironmentMode() {
//聊天
        LCChatKit.getInstance().setProfileProvider(new LeanchatUserProvider());
//bug
        BugTagUtils.start(this, BuildConfig.Environment);
//log
        LogUtils.initLogUtils(BuildConfig.Environment);

        switch (BuildConfig.Environment) {
            case Global.Environment.DEVELOP:
                LCChatKit.getInstance().initKey(getApplicationContext(), LCIMConstants.APP_ID_DEBUG, LCIMConstants.APP_KEY_DEBUG, WelcomeActivity.class);
                AVOSCloud.setDebugLogEnabled(true);
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.DEVELOP);
//LeakCanary
                LeakCanary.install(this);
//ButterKnife
                ButterKnife.setDebug(true);
                break;
            case Global.Environment.TEST:
                LCChatKit.getInstance().initKey(getApplicationContext(), LCIMConstants.APP_ID_DEBUG, LCIMConstants.APP_KEY_DEBUG, WelcomeActivity.class);
                AVOSCloud.setDebugLogEnabled(true);
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.TEST);
                break;
            case Global.Environment.PREVIEW:
                LCChatKit.getInstance().initKey(getApplicationContext(), LCIMConstants.APP_ID_PREVIEW, LCIMConstants.APP_KEY_PREVIEW, WelcomeActivity.class);
                AVOSCloud.setDebugLogEnabled(false);
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.PREVIEW);
                CrashHandler.getInstance().init(getApplicationContext());
                break;
            default:
                LCChatKit.getInstance().initKey(getApplicationContext(), LCIMConstants.APP_ID_PREVIEW, LCIMConstants.APP_KEY_PREVIEW, WelcomeActivity.class);
                AVOSCloud.setDebugLogEnabled(false);
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.PREVIEW);
                break;
        }
    }
}
