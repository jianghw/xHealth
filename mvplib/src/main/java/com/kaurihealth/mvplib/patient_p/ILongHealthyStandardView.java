package com.kaurihealth.mvplib.patient_p;

import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.mvplib.base_p.IMvpView;

import java.util.List;

/**
 * Created by jianghw on 2016/8/5.
 * <p>
 * 描述：
 */
public interface ILongHealthyStandardView extends IMvpView {
    /**
     * get DoctorPatientRelationshipBean
     *
     * @return
     */
    int getPatientId();

    void showDialogByString(String[] strings);

    void loadingDataSuccess(List<LongTermMonitoringDisplayBean> list);

    /**
     * 分发过滤的数据
     *
     * @param list
     */
    void distributionListData(List<List<LongTermMonitoringDisplayBean>> list);

    /**
     * 删除回调
     */
    void updateDataOnly();
}
