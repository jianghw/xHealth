package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface ILongHealthyStandardView extends IMvpView {
    /**
     * get DoctorPatientRelationshipBean
     *
     * @return
     */
    DoctorPatientRelationshipBean getDoctorPatientRelationshipBean();

    /**
     * 成功回调
     *
     * @param list
     */
    void drawChartsByList(List<LongTermMonitoringDisplayBean> list);
}
