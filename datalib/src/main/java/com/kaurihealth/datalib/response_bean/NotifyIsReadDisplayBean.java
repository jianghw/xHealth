package com.kaurihealth.datalib.response_bean;

/**
 * Created by jianghw on 2016/11/14.
 * <p/>
 * Describe:App和Web端 判断小红点是否显示时触发
 */

public class NotifyIsReadDisplayBean {

    /**
     * isRead : true
     * subject : sample string 2
     */

    private boolean isRead;
    private String subject;

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
