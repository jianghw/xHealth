package com.kaurihealth.kaurihealth.mine.util;

/**
 * Created by 张磊 on 2016/7/20.
 * 介绍：
 */
public enum CreditTransactionType {
    Get("提现"), Put("到款"), Error("提现失败");
    public final String value;

    CreditTransactionType(String value) {
        this.value = value;
    }
}
