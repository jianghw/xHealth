package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by mip on 2016/9/23.
 * 描述：给Homefragment设置event事件
 */
public class HomefragmentPatientEvent {

    private final int tips;

    public HomefragmentPatientEvent(int tips){
        this.tips = tips;
    }

    public int getTips(){
        return tips;
    }
}
