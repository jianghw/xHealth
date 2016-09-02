package com.example.commonlibrary.widget.util;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public interface LogUtilInterface {
    void i(String message);

    void d(String message);

    void w(String message);

    void e(String message);

    void i(String message, Throwable ex);

    void d(String message, Throwable ex);

    void w(String message, Throwable ex);

    void e(String message, Throwable ex);
}
