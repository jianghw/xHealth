package com.kaurihealth.kaurihealth.common.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 张磊 on 2016/7/26.
 * 介绍：
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RecordType {
    public boolean cansetBackground() default true;

    public boolean cansetClickAble() default true;
}
