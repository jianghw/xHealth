package com.kaurihealth.datalib.request_bean.builder;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanPatientBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;

/**
 * Created by jianghw on 2016/10/28.
 * <p/>
 * Describe:
 */

public class SearchBooleanPatientBeanBuilder {

    public SearchBooleanPatientBean Build(boolean isAdd, PatientDisplayBean mItemsBean) {
        SearchBooleanPatientBean booleanResultBean = new SearchBooleanPatientBean();
        booleanResultBean.setAdd(isAdd);
        booleanResultBean.setItemsBean(mItemsBean);
        return booleanResultBean;
    }
}
