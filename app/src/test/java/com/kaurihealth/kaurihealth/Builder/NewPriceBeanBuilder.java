package com.kaurihealth.kaurihealth.Builder;

import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/3.
 * 备注：
 */
public class NewPriceBeanBuilder {
    public NewPriceBean Build(int productId) {
        NewPriceBean newPriceBean = new NewPriceBean();
        newPriceBean.productId = productId;
        newPriceBean.description = "描叙";
        newPriceBean.isAvailable = true;
        newPriceBean.unitPrice = 20;
        return newPriceBean;
    }
}
