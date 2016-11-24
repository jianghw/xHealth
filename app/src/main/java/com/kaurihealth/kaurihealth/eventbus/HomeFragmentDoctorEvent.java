package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by KauriHealth on 2016/9/23.
 */
public class HomeFragmentDoctorEvent {

    private final int tips;

    public HomeFragmentDoctorEvent(int tips){
        this.tips = tips;
    }

    public int getTips(){
        return tips;
    }
}
