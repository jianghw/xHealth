package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 CompileProgramActivity 给  LongTermMonitoringDisplayBean
 */
public class CompileProgramBeanEvent {

    private final LongTermMonitoringDisplayBean bean;

    public CompileProgramBeanEvent(LongTermMonitoringDisplayBean bean) {
        this.bean = bean;
    }

    public LongTermMonitoringDisplayBean getBean() {
        return bean;
    }
}
