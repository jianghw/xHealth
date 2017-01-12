package com.kaurihealth.datalib.request_bean.bean;

import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;

/**
 * Created by Nick on 17/05/2016.
 */
public class SearchPatientBean {
   private boolean isAdd;
    private MainPagePatientDisplayBean mItemsBean;

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public MainPagePatientDisplayBean getItemsBean() {
        return mItemsBean;
    }

    public void setItemsBean(MainPagePatientDisplayBean itemsBean) {
        mItemsBean = itemsBean;
    }
}