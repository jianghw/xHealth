package com.example.chatlibrary.chat;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public interface ChatInterface<T> {
    /**
     * 向某个对话发送消息
     */
    void sendMessage(AVIMConversation conversation, AVIMConversationCallback callback, String message);

    /**
     * 创建两人之间的对话
     */
    void createConversation(T self, T other, AVIMConversationCreatedCallback callback);

}
