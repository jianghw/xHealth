package com.kaurihealth.kaurihealth.util;

import android.content.Context;

import com.kaurihealth.kaurihealth.util.Interface.IGetter;

/**
 * Created by KauriHealth-WEBDEV on 2016/2/3.
 * 介绍：
 */
public class Config {
    //密码规则
    public static final String pattern = "^[A-Za-z\\d$@$!%*#?&\\.]{8,20}$";
    //电话号码规则
    public static final String phonePattern = "^1[3|4|5|7|8]\\d{9}$";

    public static enum Role {
        Doctor, Patient
    }

    public static final String BugTagUserKey = "UserName";
    public static final String BugTagUserPassKey = "Password";
    public static final String BugTagPlatform = "Platform";

    /***
     * 判断是否可以编辑,只有当本地保存的userI跟传入的userId一致的时候，才可以进行编辑
     */
    public static boolean isSameUser(String userId, Context context) {
        IGetter getter = Getter.getInstance(context);
        String myUserId = String.valueOf(getter.getUserId()).trim();
        return myUserId.equals(userId);
    }
}
