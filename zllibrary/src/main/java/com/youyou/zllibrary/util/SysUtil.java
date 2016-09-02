package com.youyou.zllibrary.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.TypedValue;

/**
 * 创建时间: 2015/12/7
 * 创建人:   张磊
 * 描述:
 * 修订时间:
 */
public class SysUtil {

    /**
     * dp转px
     */
    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 获取设备DeviceID   参考：http://stackoverflow.com/questions/3802644/will-telephonymanger-getdeviceid-return-device-id-for-tablets-like-galaxy-tab
     *
     * @param context
     * @return
     */
    public static String getUniqueID(Context context) {
        String myAndroidDeviceId = "";
        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephony.getDeviceId() != null) {
            myAndroidDeviceId = mTelephony.getDeviceId();
        } else {
            myAndroidDeviceId = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return myAndroidDeviceId;
    }

    public static String getVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

}
