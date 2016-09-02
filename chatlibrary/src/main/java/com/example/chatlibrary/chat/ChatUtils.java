package com.example.chatlibrary.chat;

/**
 * Created by 张磊 on 2016/5/3.
 * 介绍：聊天工具
 */
public class ChatUtils extends ChatInjection {
    private ChatInterface chatInterface;

    public ChatInterface getChatInterface() {
        return chatInterface;
    }

    public void setChatInterface(ChatInterface chatInterface) {
        this.chatInterface = chatInterface;
    }
}
