package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.datalib.response_bean.SearchResultBean;

/**
 * Created by jianghw on 2016/10/28.
 * <p/>
 * Describe:
 */

public class SearchBooleanResultBeanBuilder {

    public SearchBooleanResultBean Build(boolean isAdd, SearchResultBean.ResultBean.ItemsBean mItemsBean) {
        SearchBooleanResultBean booleanResultBean = new SearchBooleanResultBean();
        booleanResultBean.setAdd(isAdd);
        booleanResultBean.setItemsBean(mItemsBean);
        return booleanResultBean;
    }
}
