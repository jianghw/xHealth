package com.kaurihealth.utilslib;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by jianghw on 2016/8/8.
 * <p>
 * 描述：预先判断条件工具类
 */
public class CheckUtils {
    /**
     * 确保一个对象引用作为参数传递给调用方法并不是零。
     *
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * 确保一个对象引用作为参数传递给调用方法并不是零及提示信息
     *
     * @param reference
     * @param errorMessage
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * 监测参数传递给调用方法
     * 非空判断
     *
     * @param reference
     * @param <T>
     * @return 备注信息
     */
    public static <T> T checkNullArgument(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
        return reference;
    }

    public static void checkNullStateByBoolean(Context context, boolean empty, String message) {
        if (empty) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            throw new IllegalStateException(message);
        }
    }

    public static boolean checkUrlNotNull(String url) {
        return url != null && !TextUtils.isEmpty(url) && url.trim().length() != 0;
    }
}
