package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by Garnet_Wu on 2016/10/31.
 */
public class DoctorTeamJumpEvent {

    /**
     * Created by garnet_wu on 2016/8/24.  * <p/>  * 描述：医生跳转事件
     */
    public final String message;
    private  String kauriHealthId;

    public DoctorTeamJumpEvent(String message, String kauriHealthId) {
        this.message = message;
        this.kauriHealthId = kauriHealthId;
    }
    public DoctorTeamJumpEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getKauriHealthId() {
        return kauriHealthId;
    }
}
