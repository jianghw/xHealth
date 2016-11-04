package com.kaurihealth.kaurihealth;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.bugtags.library.Bugtags;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.utilslib.bugtag.BugTagUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.SysUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Created by 张磊 on 2016/5/25.
 * 介绍：
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mCrashHandler;
    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;

    public static CrashHandler getInstance() {
        if (mCrashHandler == null) {
            mCrashHandler = new CrashHandler();
        }
        return mCrashHandler;
    }

    Context mContext;

    public void init(Context context) {
        mContext = context;
        defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        if (ex != null) {
            BugTagUtils.setUserData(Global.BugtagsConstants.BugTagUserKey, LocalData.getLocalData().getTokenBean().getUser().getUserName());
            BugTagUtils.setUserData(Global.BugtagsConstants.BugTagPlatform, LocalData.getLocalData().getEnvironment());
            BugTagUtils.setUserData(Global.BugtagsConstants.BugTagUserPassKey, SysUtil.getVersionName(mContext));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintStream(out));
            Bugtags.sendException(ex);
            //使用Toast来显示异常信息
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, "App给你开了个小玩笑,程序猿GG会尽快修复~", Toast.LENGTH_SHORT)
                        .show();
                Looper.loop();
            }
        }.start();
        try {
            Thread.sleep(2000);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        } catch (InterruptedException e) {
            Bugtags.sendException(e);
            e.printStackTrace();
        }
    }
}
