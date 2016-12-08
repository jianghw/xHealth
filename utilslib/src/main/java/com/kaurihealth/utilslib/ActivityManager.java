package com.kaurihealth.utilslib;


import android.app.Activity;

import java.util.Stack;

/**
 * Created by jianghw on 2016/10/14.
 * <p/>
 * 描述：
 */

public class ActivityManager {

    private static Stack<Activity> activityStack;

    public static ActivityManager getInstance() {
        return Singleton.INSTANCE;
    }

    private static class Singleton {
        public static final ActivityManager INSTANCE = new ActivityManager();
    }


    /**
     * 添加入栈,
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }


    /**
     * 移除
     *
     * @param activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    public void finishAllActivity() {
        if (null != activityStack) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
        }
    }

    public void finishAllActivityAndExit() {
        if (null != activityStack) {
            for (int i = 0, size = activityStack.size(); i < size; i++) {
                if (null != activityStack.get(i)) {
                    activityStack.get(i).finish();
                }
            }
            activityStack.clear();
            System.exit(0);
        }
    }
}
