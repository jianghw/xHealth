package com.kaurihealth.mvplib.base_p;

/**
 * Created by 张磊 on 2016/4/12.
 * 介绍：
 */
public interface Listener<T> {
    void error(String exception);

    void success(T response);
}
