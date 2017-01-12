package com.kaurihealth.mvplib.sickness_p;

import com.kaurihealth.datalib.request_bean.bean.LoadAllSicknessItem;
import com.kaurihealth.datalib.response_bean.AllSicknessDisplayBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/9/19.
 * <p/>
 * Describe:
 */
public interface ILoadAllSicknessView extends IMvpView {
    void loadingIndicator(boolean flag);

    void loadAllSicknessSucceed(List<AllSicknessDisplayBean> been);

    void loadAllSicknessError();

    void updateRightOptionsSucceed(List<LoadAllSicknessItem> items);

    void selectedSickDiseasesSucceed(List<SicknessesBean> been);

    void onSearchSicknessSucceed(List<SicknessesBean> been);
}
