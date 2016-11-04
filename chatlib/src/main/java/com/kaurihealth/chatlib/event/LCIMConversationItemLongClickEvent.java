package com.kaurihealth.chatlib.event;

import com.avos.avoscloud.im.v2.AVIMConversation;

/**
 * Created by wli on 16/9/14.
 *
 * @see LCIMConversationItemHolder
 * @see LCIMConversationListFragment
 */
public class LCIMConversationItemLongClickEvent {
    public AVIMConversation conversation;

    public LCIMConversationItemLongClickEvent(AVIMConversation conversation) {
        this.conversation = conversation;
    }
}
