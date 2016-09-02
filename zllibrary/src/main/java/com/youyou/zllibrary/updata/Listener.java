package com.youyou.zllibrary.updata;

/**
 * Created by 张磊 on 2016/4/12.
 * 介绍：
 */
public interface  Listener<T> {
    void error(String exception);

    void success(T response);
}
