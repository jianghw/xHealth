package com.example.chatlibrary.chat;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.chatlibrary.bean.ChatBean;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public class SingleChat implements ChatInterface<ChatBean> {
    public final static String AttributeKey = "members";
    LogUtilInterface logUtil = LogFactory.getSimpleLog("SingleChat");
    AVIMClient client;
    private ChatIsAvailableInterface control;

    public SingleChat(AVIMClient client, ChatIsAvailableInterface control) {
        this.client = client;
        this.control = control;
    }

    @Override
    public void sendMessage(AVIMConversation conversation, AVIMConversationCallback callback, String message) {
        if (!(control.isAvailable())) {
            return;
        }
        AVIMTextMessage msg = new AVIMTextMessage();
        msg.setText(message);
        if (conversation != null) {
            conversation.sendMessage(msg, callback);
        }
    }

    //TODO JIANGHE  ChatBean  attributes - 额外属性
    @Override
    public void createConversation(ChatBean self, ChatBean other, AVIMConversationCreatedCallback callback) {
        if (!(control.isAvailable())) {
            return;
        }
        List<String> members = new LinkedList<>();
        String name = self.getClientId() + " & " + other.getClientId();
        ChatBean[] chatBeens = new ChatBean[2];
        chatBeens[0] = self;
        chatBeens[1] = other;

        Map<String, Object> param = new HashMap<>();
        param.put("members", chatBeens);

        members.add(self.getClientId());
        members.add(other.getClientId());
        client.createConversation(members, name, param, false, true, callback);
    }

}
