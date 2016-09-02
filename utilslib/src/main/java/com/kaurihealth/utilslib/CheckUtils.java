package com.kaurihealth.utilslib;

import android.support.annotation.Nullable;

/**
 * Created by jianghw on 2016/8/8.
 * <p/>
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
}
