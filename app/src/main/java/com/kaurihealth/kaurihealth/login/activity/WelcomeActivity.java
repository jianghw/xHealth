package com.kaurihealth.kaurihealth.login.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.bugtags.library.Bugtags;
import com.example.chatlibrary.chat.ChatInjection;
import com.kaurihealth.kaurihealth.BuildConfig;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.common.activity.MainActivity;
import com.kaurihealth.kaurihealth.util.ExitApplication;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.youyou.zllibrary.util.CommonActivity;
import com.youyou.zllibrary.util.SpUtil;

import butterknife.ButterKnife;


/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class WelcomeActivity extends CommonActivity {
    private Handler handler = new Handler();
    private int delayTime;
    private SpUtil spUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if (BuildConfig.Environment.equals("Develop")) {
            delayTime = 600;
        } else {
            delayTime = 3000;
        }
        ButterKnife.bind(this);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            if (packageInfo != null) {
            }
        } catch (PackageManager.NameNotFoundException e) {
            Bugtags.sendException(e);
            e.printStackTrace();
        }
        try {
            if (hasLogin()) {
                connectLeanClound();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skipTo(MainActivity.class);
                        finishCur();
                    }
                }, delayTime);

            } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skipTo(LoginActivity.class);
                        finishCur();
                    }
                }, 1200);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        ExitApplication.getInstance().addActivity(this);
    }

    private boolean hasLogin() throws IllegalAccessException {
        spUtil = SpUtil.getInstance(getApplicationContext());
        Boolean aBoolean = spUtil.get("Auto", Boolean.class);
        return aBoolean;
    }

    private void connectLeanClound() {
        IGetter getter=Getter.getInstance(getApplicationContext());
        ChatInjection.chatConnect(getter.getKauriHealthId(), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e != null) {
                    showToast("连接留言服务器失败");
                } else {
                    ChatInjection.avimClient = avimClient;
                }
            }
        });
    }
}

