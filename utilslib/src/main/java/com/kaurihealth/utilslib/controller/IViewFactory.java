package com.kaurihealth.utilslib.controller;

import android.view.ViewGroup;

/**
 * Created by jianghw on 2016/12/16.
 * <p/>
 * Describe:
 */

public interface IViewFactory<T> {

    void createIncludeViews();

    void attachRootView(ViewGroup layContent);

    /**
     *
     * @param beanData
     * @param position 0时统一数据加载 1,2,3...
     */
    void fillNewestData(T beanData,int position);

    void unbindView();

    void showLayout();

    void hiddenLayout();
}
