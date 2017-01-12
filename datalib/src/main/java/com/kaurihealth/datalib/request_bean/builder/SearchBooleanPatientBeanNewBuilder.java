package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.SearchPatientBean;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;

/**
 * Created by mip on 2017/1/4.
 * <p/>
 * Describe:
 */

public class SearchBooleanPatientBeanNewBuilder {

    public SearchPatientBean Build(boolean isAdd, MainPagePatientDisplayBean mItemsBean) {
        SearchPatientBean booleanResultBean = new SearchPatientBean();
        booleanResultBean.setAdd(isAdd);
        booleanResultBean.setItemsBean(mItemsBean);
        return booleanResultBean;
    }
}
