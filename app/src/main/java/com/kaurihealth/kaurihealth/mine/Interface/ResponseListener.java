package com.kaurihealth.kaurihealth.mine.Interface;

/**
 * Created by 米平 on 2016/7/7.
 */
public interface ResponseListener<T> {
    void erorr(String erorr);

    void success(T response);

}
