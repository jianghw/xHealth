package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface ICompileProgramView extends IMvpView {

    List<LongTermMonitoringDisplayBean> getNewLongtermMonitoringBean();
}
