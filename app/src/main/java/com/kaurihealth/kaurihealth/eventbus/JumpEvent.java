package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by jianghw on 2016/8/24.
 * <p/>
 * 描述：
 */
public class JumpEvent {

    public final String message;

    public JumpEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
