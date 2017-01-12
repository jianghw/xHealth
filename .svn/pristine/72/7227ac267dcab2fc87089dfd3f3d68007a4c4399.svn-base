package com.kaurihealth.mvplib.sickness_p;

import com.kaurihealth.datalib.response_bean.AllSicknessDisplayBean;
import com.kaurihealth.datalib.response_bean.ChildDepartmentsBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.mvplib.base_p.IMvpPresenter;

import java.util.List;

/**
 * Created by jianghw on 2016/9/21.
 * <p/>
 * Describe:
 */
public interface ILoadAllSicknessPresenter<V> extends IMvpPresenter<V> {
    void loadingRemoteData(boolean isDirty);

    void updateRightOptions(List<ChildDepartmentsBean> departments);

    void selectedSickDiseases(List<SicknessesBean> list, SicknessesBean bean);

    void onSearchSickness(List<AllSicknessDisplayBean> been, CharSequence text);
}
