package com.kaurihealth.kaurihealth.mine.util;

/**
 * Created by 张磊 on 2016/7/20.
 * 介绍：
 */
public interface IGetAmountDetail<T> {
    void success(T t);

    void complete();
}
