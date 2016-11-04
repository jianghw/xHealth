package com.kaurihealth.mvplib.home_p;

import com.kaurihealth.datalib.request_bean.bean.InitialiseSearchRequestBean;
import com.kaurihealth.datalib.request_bean.bean.SearchBooleanResultBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/9/18.
 * <p/>
 * Describe:
 */
public interface ISearchActivityView extends IMvpView{

     void updataDataSucceed(List<SearchBooleanResultBean> been);

     String getEditTextSearch();

     InitialiseSearchRequestBean getCurrentSearchRequestBean();
}
