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
                .trackingLocation(true)   //是否获取位置，默认 true
                .trackingCrashLog(true)       //是否收集闪退，默认 true
                .trackingConsoleLog(true)  //是否收集控制台日志，默认 true
                .trackingUserSteps(true)     // //是否跟踪用户操作步骤，默认 true
                .versionName(packageInfo.versionName)   //自定义版本名称，默认 app versionName
                .versionCode(packageInfo.versionCode)  //自定义版本号，默认 app versionCode
                .trackingNetworkURLFilter("(.*)").build();  //自定义网络请求跟踪的 url 规则，默认 null
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
