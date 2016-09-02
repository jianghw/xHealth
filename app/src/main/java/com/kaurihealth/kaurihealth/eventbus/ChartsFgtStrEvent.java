package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：传 programs[index] 给  ChartsFragment
 */
public class ChartsFgtStrEvent {

    private final String mString;

    public ChartsFgtStrEvent(String message) {
        this.mString = message;
    }

    public String getMessage() {
        return mString;
    }
}
