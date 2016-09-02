package com.kaurihealth.kaurihealth;

import android.content.Context;

import com.bugtags.library.Bugtags;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;

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

    LogUtilInterface logUtil = LogFactory.getSimpleLog(CrashHandler.class.getName());

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        if (ex != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ex.printStackTrace(new PrintStream(out));
            logUtil.e(out.toString());
            Bugtags.sendException(ex);
            //使用Toast来显示异常信息
        }
//        new Thread() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                Toast.makeText(mContext, "程序出错啦。。。", Toast.LENGTH_SHORT)
//                        .show();
//                Looper.loop();
//            }
//        }.start();
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
