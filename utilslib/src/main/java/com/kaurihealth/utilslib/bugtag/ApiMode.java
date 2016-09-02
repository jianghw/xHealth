package com.kaurihealth.utilslib.bugtag;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public enum ApiMode {
    Test("测试版"),
    Develop("开发版"),//开发时候的版本
    Preview("预览版"),//预览版
    Stable("正式版");//正式版本

    private String value;

    @Override
    public String toString() {
        return value;
    }

    ApiMode(String value) {
        this.value = value;
    }
}
