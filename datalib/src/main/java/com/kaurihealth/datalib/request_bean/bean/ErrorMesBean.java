package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by 张磊 on 2016/5/27.
 * 介绍：
 */
public class ErrorMesBean {

    /**
     * message : 用户不存在
     * status : 3
     * isSucess : false
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
