package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.kaurihealth.record_details_v.bean.HealthyRecordNewActivityBean;

/**
 * Created by mip on 2016/12/19.
 * 描述：
 */

public class HealthConditionFragmentAddEvent {

    private HealthyRecordNewActivityBean mActivityBean;

    public HealthConditionFragmentAddEvent(HealthyRecordNewActivityBean activityBean) {
        this.mActivityBean = activityBean;
    }

    public HealthyRecordNewActivityBean getActivityBean() {
        return mActivityBean;
    }
}
