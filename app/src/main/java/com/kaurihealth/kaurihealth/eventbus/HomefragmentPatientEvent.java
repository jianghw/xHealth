package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by mip on 2016/9/23.
 * 描述：给Homefragment设置event事件
 */
public class HomefragmentPatientEvent {

    final String tips;

    public HomefragmentPatientEvent(String tips){
        this.tips = tips;
    }

    public String getTips(){
        return tips;
    }
}
