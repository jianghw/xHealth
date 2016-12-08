package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by Garnet_Wu on 2016/11/24.
 */
public class HospitalNameEvent {
    private final String   updateMessage;

    public HospitalNameEvent(String  updateMessage){
        this.updateMessage = updateMessage;
    }

    public String getupdateMessage(){
        return updateMessage;
    }
}
