package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by Garnet_Wu on 2016/11/1.
 *
 */
public class VerificationJumpEvent {
    public final String message;

    public VerificationJumpEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
