package com.youyou.zllibrary.util;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * 创建时间: 2015/12/3
 * 创建人:   张磊
 * 描述:
 * 修订时间:
 */
public interface CommonUtilInterface {
    void showToast(CharSequence message);

    void init();

    void debugShowToast(CharSequence message);

    <T> void put(String key, T value);

    <T> T get(String key, Class<T> tClass) throws IllegalAccessException, IllegalArgumentException;

    void setBack(int id);

    void finishCur();

    void showSnackBar(String mes, int time);

    void showSnackBar(String mes);

    void hideSoftInput();

    void showValidationMessage(List<ValidationError> errors);
}
