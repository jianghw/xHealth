package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.mvplib.base_p.IMvpPresenter;

/**
 * Created by Nick on 22/08/2016.
 */
public interface ISearchPresenter<V> extends IMvpPresenter<V> {
    //点击 界面右上角的搜索按钮
    void  clickSearchButton(String searchKeywords);
}
