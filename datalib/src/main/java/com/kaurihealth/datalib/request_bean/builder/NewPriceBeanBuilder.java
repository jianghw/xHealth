package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.NewPriceBean;

/**
 * Created by Nick on 31/08/2016.
 */
public class NewPriceBeanBuilder {
    public NewPriceBean Build(int productId, double unitPrice) {
        NewPriceBean newPriceBean = new NewPriceBean();
        newPriceBean.productId = productId;
        newPriceBean.description = "描叙";
        newPriceBean.isAvailable = true;
        newPriceBean.unitPrice = unitPrice;
        return newPriceBean;
    }
}
