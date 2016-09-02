package com.kaurihealth.kaurihealth.common.Interface;

/**
 * Created by 张磊 on 2016/7/19.
 * 介绍：
 */
public interface IAddRecord<T> {
    boolean isImageComplete();

    void setImagsUpLoadCompleted(boolean isImagsComplete);

    T getBean();

    void commit(T t);

    boolean isWaite();

    void setIsWaite(boolean isWaite);
}
