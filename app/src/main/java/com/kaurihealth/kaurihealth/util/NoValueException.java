package com.kaurihealth.kaurihealth.util;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public class NoValueException extends Exception {
    public NoValueException() {
        super();
    }

    public NoValueException(String detail) {
        super(detail);
    }
}
