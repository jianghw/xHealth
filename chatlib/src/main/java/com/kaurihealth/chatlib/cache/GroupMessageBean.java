package com.kaurihealth.chatlib.cache;

import com.avos.avoscloud.im.v2.AVIMMessage;

import java.util.List;

/**
 * Created by jianghw on 2016/11/16.
 * <p/>
 * Describe:
 */

public class GroupMessageBean {

    private List<String> members;
    private String name;
    private AVIMMessage mMessages;

    public GroupMessageBean(String name, AVIMMessage messages) {
        this(name, messages, null);
    }

    public GroupMessageBean(String name, AVIMMessage messages, List<String> members) {
        this.name = name;
        this.mMessages = messages;
        this.members = members;
    }

    public AVIMMessage getMessages() {
        return mMessages;
    }

    public void setMessages(AVIMMessage messages) {
        mMessages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }
}
