package com.kaurihealth.kaurihealth.eventbus;

import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;

import java.util.List;

/**
 * Created by jianghw on 2016/8/24.
 * <p>
 * 描述：传 programs[index] 给  ChartsFragment
 */
public class ChartsFgtStrEvent {

    private final List<List<LongTermMonitoringDisplayBean>> mString;

    public ChartsFgtStrEvent(List<List<LongTermMonitoringDisplayBean>> message) {
        this.mString = message;
    }

    public List<List<LongTermMonitoringDisplayBean>> getListsMessage() {
        return mString;
    }
}
