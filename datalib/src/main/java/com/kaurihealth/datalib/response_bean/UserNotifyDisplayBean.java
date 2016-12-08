package com.kaurihealth.datalib.response_bean;

import java.util.Date;

/**
 * Created by jianghw on 2016/11/14.
 * <p/>
 * Describe:查询该用户的所有系统消息
 */

public class UserNotifyDisplayBean {

    /**
     * userNotifyId : 1
     * isRead : true
     * userId : 3
     * notifyId : 4
     * createdAt : 2016-11-14T14:47:43+08:00
     * isDelete : true
     * notifyDisplayDto : {"notifyId":1,"notifyType":0,"createdAt":"2016-11-14T14:47:43+08:00","content":"sample string 3","sender":"sample string 4","subject":"sample string 5"}
     */

    private int userNotifyId;
    private boolean isRead;
    private int userId;
    private int notifyId;
    private Date createdAt;
    private boolean isDelete;
    /**
     * notifyId : 1
     * notifyType : 0
     * createdAt : 2016-11-14T14:47:43+08:00
     * content : sample string 3
     * sender : sample string 4
     * subject : sample string 5
     */

    private NotifyDisplayBean notifyDisplayDto;

    public int getUserNotifyId() {
        return userNotifyId;
    }

    public void setUserNotifyId(int userNotifyId) {
        this.userNotifyId = userNotifyId;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNotifyId() {
        return notifyId;
    }

    public void setNotifyId(int notifyId) {
        this.notifyId = notifyId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public NotifyDisplayBean getNotifyDisplayDto() {
        return notifyDisplayDto;
    }

    public void setNotifyDisplayDto(NotifyDisplayBean notifyDisplayDto) {
        this.notifyDisplayDto = notifyDisplayDto;
    }

}
