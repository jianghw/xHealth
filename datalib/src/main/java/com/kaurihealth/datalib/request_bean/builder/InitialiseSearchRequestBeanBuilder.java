package com.kaurihealth.datalib.request_bean.builder;


import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;

/**
 * Created by Nick on 17/05/2016.
 */
public class InitialiseSearchRequestBeanBuilder {
    public InitialiseSearchRequestBean Build(String scope, String keyword) {
        InitialiseSearchRequestBean initialiseSearchRequestBean = new InitialiseSearchRequestBean();
        initialiseSearchRequestBean.scope = scope;
        initialiseSearchRequestBean.keyword = keyword;
        initialiseSearchRequestBean.start = 0;
        initialiseSearchRequestBean.hit = 100;
        return initialiseSearchRequestBean;
    }
}
