package com.kaurihealth.mvplib.doctor_new;

import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by mip on 2016/12/13.
 */

public interface IDoctorView  extends IMvpView{

    void loadingIndicator(boolean flag);

    void lazyLoadingDataSuccess(List<DoctorRelationshipBean> list);

    void isHasDoctorRequest(boolean b);

}
