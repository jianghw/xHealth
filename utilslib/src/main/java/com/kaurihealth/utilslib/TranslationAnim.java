package com.kaurihealth.utilslib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 *
 */
public class TranslationAnim {
    /**
     * 启动一个新的Activity
     */
    public static void zlStartActivity(Activity sourse, Class<? extends Activity> purpose, Bundle bundle, int enterAnim, int exitAnim) {
        Intent intent = new Intent(sourse, purpose);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sourse.startActivity(intent);
        sourse.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动一个新的Activity
     */
    public static void zlStartActivityForResult(Activity sourse, Class<? extends Activity> purpose, Bundle bundle, int requestCode, int enterAnim, int exitAnim) {
        Intent intent = new Intent(sourse, purpose);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sourse.startActivityForResult(intent, requestCode);
        sourse.overridePendingTransition(enterAnim, exitAnim);
    }

    public static void zlStartActivityForResult(Activity sourse, Intent intent, Bundle bundle, int requestCode, int enterAnim, int exitAnim) {
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        sourse.startActivityForResult(intent, requestCode);
        sourse.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 关闭当前Activity
     */
    public static void zlFinish(Activity context, int enterAnim, int exitAnim) {
        context.finish();
        context.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * 启动一个新的Activity  动画旋转
     */
    public static void zlStartActivityRound(Activity sourse, Class<? extends Activity> purpose, Bundle bundle) {
        zlStartActivity(sourse, purpose, bundle, R.anim.activity_open_enter, R.anim.activity_open_exit);
    }

    /**
     * 关闭当前Activity     动画旋转
     */
    public static void zlFinishRound(Activity context) {
        zlFinish(context, R.anim.activity_close_enter, R.anim.activity_close_exit);
    }

    /**
     * 启动一个新的Activity
     */
    public static void zlStartActivity(Activity sourse, Class<? extends Activity> purpose, Bundle bundle) {
        zlStartActivity(sourse, purpose, bundle, R.anim.open_to_left, R.anim.exit_to_left);
    }

    /**
     * 启动一个新的Activity
     */
    public static void zlStartActivityForResult(Activity sourse, Class<? extends Activity> purpose, Bundle bundle, int requestCode) {
        zlStartActivityForResult(sourse, purpose, bundle, requestCode, R.anim.open_to_left, R.anim.exit_to_left);
    }

    public static void zlStartActivityForResult(Activity sourse, Intent intent, Bundle bundle, int requestCode) {
        zlStartActivityForResult(sourse, intent, bundle, requestCode, R.anim.open_to_left, R.anim.exit_to_left);
    }

    /**
     * 关闭当前Activity
     */
    public static void zlFinish(Activity context) {
        zlFinish(context, R.anim.open_to_right, R.anim.exit_to_right);
    }
}
