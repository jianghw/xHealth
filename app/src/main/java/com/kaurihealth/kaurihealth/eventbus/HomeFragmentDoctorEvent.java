package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by KauriHealth on 2016/9/23.
 */
public class HomeFragmentDoctorEvent {
    final String tips;

    public HomeFragmentDoctorEvent(String tips){
        this.tips = tips;
    }

    public String getTips(){
        return tips;
    }
}
