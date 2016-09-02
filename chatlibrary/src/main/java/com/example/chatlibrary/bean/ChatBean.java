package com.example.chatlibrary.bean;

import java.io.Serializable;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public class ChatBean implements Serializable{

    /**
     * clientId : c86f8f36173e4b878bd8710fe9ca7d68
     * fullName : wangcong002
     * avatar : http://kaurihealthrecordimagetest.kaurihealth.com/c86f8f36173e4b878bd8710fe9ca7d68/Icon/80345b9a71584b22a940835c5e157fa8.jpeg
     */

    private String clientId;
    private String fullName;
    private String avatar;
    private String userType;

    public ChatBean(String clientId, String fullName, String avatar) {
        this.clientId = clientId;
        this.fullName = fullName;
        this.avatar = avatar;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
