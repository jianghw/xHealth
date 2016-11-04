package com.kaurihealth.chatlib.event;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.kaurihealth.chatlib.fragment.LCIMConversationListFragment;
import com.kaurihealth.chatlib.handler.LCIMConversationHandler;

/**
 * Created by wli on 16/3/7.
 * 离线消息数量发生变化的事件
 *
 * @see LCIMConversationHandler
 * @see LCIMConversationListFragment
 */
public class LCIMOfflineMessageCountChangeEvent {
    private final int unreadCount;
    private final int readCount;
    public AVIMConversation conversation;

    public LCIMOfflineMessageCountChangeEvent(int unreadCount, int readCount) {
        this.unreadCount = unreadCount;
        this.readCount = readCount;
    }

    public int getUnreadCount() {
        return unreadCount;
    }

    public int getReadCount() {
        return readCount;
    }
}
