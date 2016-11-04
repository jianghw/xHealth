package com.kaurihealth.mvplib.main_p;

import android.graphics.Bitmap;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

/**
 * Created by Nick on 23/08/2016.
 */
public interface IMineView extends IMvpView {

    void loadingIndicator(boolean flag);

    /**
     * 加载数据成功回调
     *
     * @param doctorDisplayBean
     */
    void loadDoctorDataSucceed(DoctorDisplayBean doctorDisplayBean);

    void gaussianBlur(Bitmap bitmap);
}
