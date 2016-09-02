package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.mvplib.base_p.IMvpView;
import com.kaurihealth.utilslib.date.RemainTimeBean;

/**
 * Created by jianghw on 2016/8/5.
 * <p/>
 * 描述：
 */
public interface IPatientInfoView extends IMvpView {
    /**
     * 倒计时
     *
     * @param bean
     */
    void showCountdown(RemainTimeBean bean);

    /**
     * @return mDoctorPatientRelationshipBean
     */
    DoctorPatientRelationshipBean getDoctorPatientRelationshipBean();

    void setFloatingActionButton(boolean active);

    /**
     * 更新数据
     *
     * @param bean
     */
    void updateBeanData(DoctorPatientRelationshipBean bean);
}
