package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：跳转事件
 */
public class PatientJumpEvent {

    public final String message;
    private final String kauriHealthId;

    public PatientJumpEvent(String message,String kauriHealthId) {
        this.message = message;
        this.kauriHealthId = kauriHealthId;
    }

    public String getMessage() {
        return message;
    }

    public String getKauriHealthId() {
        return kauriHealthId;
    }
}
