package com.kaurihealth.mvplib.search_p;

import android.support.v4.view.ViewPager;

import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Nick on 22/08/2016.
 */
public interface ISearchView extends IMvpView {

    //拿到搜索界面的关键字集合
    String[] getKeyWordsList();

    //搜索首页: 所有,医院, 医生, 科室
    InitialiseSearchRequestBean getInitialiseSearchRequestBean();

   //获得ViewPager索引
   int getIndex();

}
