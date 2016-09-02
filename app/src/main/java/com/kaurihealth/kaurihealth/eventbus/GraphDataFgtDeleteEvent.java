package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 LongTermMonitoringDisplayBean 给  GraphDataFragment
 */
public class GraphDataFgtDeleteEvent {

    private final String message;
    private final LongTermMonitoringDisplayBean bean;


    public GraphDataFgtDeleteEvent(String message, LongTermMonitoringDisplayBean bean) {
        this.message = message;
        this.bean = bean;
    }

    public String getMessage() {
        return message;
    }


    public LongTermMonitoringDisplayBean getBean() {
        return bean;
    }
}
