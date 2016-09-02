package com.kaurihealth.kaurihealth;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.example.chatlibrary.chat.ChatInjection;
import com.kaurihealth.kaurihealth.dagger.component.DaggerRepositoryComponent;
import com.kaurihealth.kaurihealth.dagger.component.RepositoryComponent;
import com.kaurihealth.kaurihealth.dagger.module.ApplicationModule;
import com.kaurihealth.kaurihealth.dagger.module.RepositoryModule;
import com.kaurihealth.utilslib.SharedUtils;
import com.kaurihealth.utilslib.bugtag.ApiMode;
import com.kaurihealth.utilslib.bugtag.BugTagUtils;
import com.kaurihealth.utilslib.bugtag.EnvironmentConfig;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.leakcanary.LeakCanary;
import com.youyou.zllibrary.httputil.HttpUtilNewOne;


/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class MyApplication extends Application {

    private RepositoryComponent mRepositoryComponent;
    private static MyApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        //分dex
        MultiDex.install(this);

        initImageLoad();

        setEnvironmentMode();

        ChatInjection.init(getApplicationContext(), BuildConfig.Environment);

        HttpUtilNewOne.init(getApplicationContext());

        BugTagUtils.start(this, BuildConfig.Environment);

        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());

        LogUtils.initLogUtils(BuildConfig.Environment);

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
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    private void setEnvironmentMode() {
        switch (BuildConfig.Environment) {
            case Global.Environment.DEVELOP:
                EnvironmentConfig.CurVersion = ApiMode.Develop;
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.DEVELOP);
                //LeakCanary
                LeakCanary.install(this);
                break;
            case Global.Environment.TEST:
                EnvironmentConfig.CurVersion = ApiMode.Test;
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.TEST);
                break;
            case Global.Environment.PREVIEW:
                EnvironmentConfig.CurVersion = ApiMode.Preview;
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.PREVIEW);
                break;
            default:
                SharedUtils.setString(this, Global.Environment.ENVIRONMENT, Global.Environment.PREVIEW);
                break;
        }
    }
}
