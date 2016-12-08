package com.kaurihealth.chatlib.event;

/**
 * Created by wli on 16/9/14.
 * <p/>
 * Describe:
 */
public class LCIMConversationItemLongClickEvent {
    public String conversation;

    public LCIMConversationItemLongClickEvent(String conversationId) {
        this.conversation = conversationId;
    }
}
