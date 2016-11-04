package com.kaurihealth.datalib.response_bean;

public class InitiateVerificationResponse {
    /// <summary>
    /// 消息
    /// </summary>
    private String message;

    /// <summary>
    /// 是否成功
    /// </summary>
    private boolean isSuccessful;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
