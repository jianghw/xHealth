package com.kaurihealth.mvplib.main_p;


import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface IPatientView extends IMvpView {

    void loadingIndicator(boolean flag);

    void lazyLoadingDataSuccess(List<?> list);
}
