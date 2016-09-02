package com.kaurihealth.kaurihealth.util.Enums;

/**
 * Created by 张磊 on 2016/7/20.
 * 介绍：
 */
public enum LoadingStatu {
    Success("获取数据成功"), GetDataError("获取数据失败"), NetError("访问网络失败");
    public String value;

    private LoadingStatu(String value) {
        this.value = value;
    }
}
