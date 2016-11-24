package com.kaurihealth.chatlib.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.kaurihealth.chatlib.fragment.LCIMConversationListFragment;
import com.kaurihealth.chatlib.handler.LCIMMessageHandler;

/**
 * Created by wli on 15/8/23.
 * 收到 AVIMTypedMessage 消息后的事件
 *
 * @see LCIMMessageHandler
 * @see LCIMConversationListFragment
 */
public class LCIMIMTypeMessageEvent {

    public AVIMTypedMessage message;
    public AVIMConversation conversation;

    public LCIMIMTypeMessageEvent() {
    }

    public LCIMIMTypeMessageEvent(AVIMTypedMessage message, AVIMConversation conversation) {
        this.message = message;
        this.conversation = conversation;
    }
}
