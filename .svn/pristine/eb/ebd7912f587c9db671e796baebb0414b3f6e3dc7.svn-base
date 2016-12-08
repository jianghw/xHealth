package com.kaurihealth.chatlib.event;

import com.kaurihealth.chatlib.fragment.LCIMConversationListFragment;
import com.kaurihealth.chatlib.handler.LCIMMessageHandler;

import java.util.List;

/**
 * Created by wli on 15/8/23.
 * 收到 踢人时的通知
 *
 * @see LCIMMessageHandler
 * @see LCIMConversationListFragment
 */
public class LCIMIMKickedMessageEvent {

    private final String conversationId;
    private final List<String> members;
    private final String invitedBy;

    public LCIMIMKickedMessageEvent(String conversationId, List<String> members, String invitedBy) {
        this.conversationId = conversationId;
        this.members = members;
        this.invitedBy = invitedBy;
    }

    public String getConversationId() {
        return conversationId;
    }

    public List<String> getMembers() {
        return members;
    }

    public String getInvitedBy() {
        return invitedBy;
    }
}
