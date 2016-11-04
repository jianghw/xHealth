package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/9/19.
 */
public interface ISearchCommonFragmentView extends IMvpView {
    List<SearchBooleanResultBean> getSearchList();

    String getTitle();

    String getEditTextContent();

    void processingData(List<SearchBooleanResultBean> list);
}
