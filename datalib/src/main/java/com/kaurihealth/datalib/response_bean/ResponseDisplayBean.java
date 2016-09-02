package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/8/31.
 * <p/>
 * 描述：
 */
public class ResponseDisplayBean {

    /**
     * message : sample string 1
     * status : 0
     * isSucess : true
     */

    private String message;
    private int status;
    private boolean isSucess;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isIsSucess() {
        return isSucess;
    }

    public void setIsSucess(boolean isSucess) {
        this.isSucess = isSucess;
    }
}
