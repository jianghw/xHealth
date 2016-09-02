package com.kaurihealth.mvplib.main_p;


import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface IPatientView extends IMvpView {

    void loadingIndicator(boolean flag);

    void lazyLoadingDataError(String message);

    void lazyLoadingDataSuccess(List<DoctorPatientRelationshipBean> list);
}
