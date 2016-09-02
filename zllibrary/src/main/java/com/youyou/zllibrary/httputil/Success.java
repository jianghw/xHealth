package com.youyou.zllibrary.httputil;

/**
 * Created by 张磊 on 2016/4/18.
 * 介绍：
 */
public interface Success<T> {
    void success(T t) throws Exception;
}
