package com.kaurihealth.utilslib.bugtag;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.kaurihealth.utilslib.constant.Global;


/**
 * Created by 张磊 on 2016/5/25.
 * 介绍：
 */
public class BugTagUtils {
    public static void start(Application application, String environment) {
        try {
            switch (environment) {
                case Global.Environment.DEVELOP:
                    break;
                case Global.Environment.TEST:
                    Bugtags.start(BugTagConfig.getBugTagAppKey(environment), application, Bugtags.BTGInvocationEventShake, getBugTagConfig(application));
                    break;
                case Global.Environment.PREVIEW:
                    Bugtags.start(BugTagConfig.getBugTagAppKey(environment), application, Bugtags.BTGInvocationEventNone, getBugTagConfig(application));
                    break;
                default:
                    break;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static BugtagsOptions getBugTagConfig(Context context) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        BugtagsOptions options = (new BugtagsOptions.Builder())
                .trackingLocation(true)
                .trackingCrashLog(true)
                .trackingConsoleLog(true)
                .trackingUserSteps(true)
                .versionName(packageInfo.versionName)
                .versionCode(packageInfo.versionCode)
                .trackingNetworkURLFilter("(.*)").build();
        return options;
    }

    //设置账号信息
    public static void setUserData(String key, String value) {
        Bugtags.setUserData(key, value);
    }

    public static void sendException(Throwable throwable){
        Bugtags.sendException(throwable);
    }
}
