package com.kaurihealth.chatlib.handler;

import android.content.Context;
import android.content.Intent;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.R;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.chatlib.event.LCIMIMTypeMessageEvent;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.chatlib.utils.LCIMLogUtils;
import com.kaurihealth.chatlib.utils.LCIMNotificationUtils;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.utilslib.log.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 消息处理 handler   消息来啦~~
 * AVIMTypedMessage 的 handler，socket 过来的 AVIMTypedMessage 都会通过此 handler 与应用交互
 * 需要应用主动调用 AVIMMessageManager.registerMessageHandler 来注册
 * 当然，自定义的消息也可以通过这种方式来处理
 */
public class LCIMMessageHandler extends AVIMTypedMessageHandler<AVIMTypedMessage> {

    private Context context;

    public LCIMMessageHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * message.getFrom()--873a487e43204f7d90ffe1d0a52666c520160923111921 这里可能对应的为他getKauriHealthId
     * client.getClientId()--这里可能对应的为我getKauriHealthId
     * message.getConversationId()--57f8c5b48159ccabfc385c14
     * message.getContent()--{"_lcattrs":{"username":"小哥哥"},"_lctext":"你的时候你会害怕","_lctype":-1}
     * conversation.getConversationId()--57f8c5b48159ccabfc385c14
     */
    @Override
    public void onMessage(final AVIMTypedMessage message, final AVIMConversation conversation, AVIMClient client) {
        if (message == null || message.getMessageId() == null) {
            LCIMLogUtils.d("may be SDK Bug, messae or message id is null");
            return;
        }
        if (LCChatKit.getInstance().getCurrentUserId() == null) {
            LCIMLogUtils.d("selfId is null, please call LCChatKit.open!");
            client.close(null);
        } else {
            if (!client.getClientId().equals(LCChatKit.getInstance().getCurrentUserId())) {
                client.close(null);
            } else {//message.getFrom()--给我发信息的人
                if (!message.getFrom().equals(client.getClientId())) {
                    LCIMConversationItemCache.getInstance().increaseUnreadCount(message.getConversationId());
                    if (LCIMNotificationUtils.isShowNotification(conversation.getConversationId())) {
                        sendNotification(message, conversation);
                    }
                    sendEvent(message, conversation);
                } else {
                    LCIMConversationItemCache.getInstance().insertConversation(message.getConversationId());
                }
            }
        }
    }

    @Override
    public void onMessageReceipt(AVIMTypedMessage message, AVIMConversation conversation, AVIMClient client) {
        super.onMessageReceipt(message, conversation, client);
    }

    /**
     * 发送消息到来的通知事件
     *
     * @param message
     * @param conversation
     */
    private void sendEvent(AVIMTypedMessage message, AVIMConversation conversation) {
        EventBus.getDefault().post(new LCIMIMTypeMessageEvent(message,conversation));
    }

    private void sendNotification(final AVIMTypedMessage message, final AVIMConversation conversation) {
        if (null != conversation && null != message) {
            final String notificationContent = message instanceof AVIMTextMessage ? ((AVIMTextMessage) message).getText() : context.getString(R.string.lcim_unspport_message_type);
            showNotification(message, conversation, notificationContent);
        }
    }

    private void showNotification(final AVIMTypedMessage message, final AVIMConversation conversation, final String notificationContent) {
        LCIMProfileCache.getInstance().getCachedUser(message.getFrom(), new AVCallback<ContactUserDisplayBean>() {
            @Override
            protected void internalDone0(ContactUserDisplayBean userProfile, AVException e) {
                if (e != null) {
                    LogUtils.e(e.getMessage());
                } else if (null != userProfile) {
                    String title = userProfile.getFullName();
                    Intent intent = getIMNotificationIntent(conversation.getConversationId(), message.getFrom());
                    LCIMNotificationUtils.showNotification(context, title, notificationContent, null, intent);
                }
            }
        });
    }

    /**
     * 点击 notification 触发的 Intent
     * 注意要设置 package 已经 Category，避免多 app 同时引用 lib 造成消息干扰
     *
     * @param conversationId
     * @param peerId
     * @return
     */
    private Intent getIMNotificationIntent(String conversationId, String peerId) {
        Intent intent = new Intent();
        intent.setAction(LCIMConstants.CHAT_NOTIFICATION_ACTION);
        intent.putExtra(LCIMConstants.CONVERSATION_ID, conversationId);
        intent.putExtra(LCIMConstants.PEER_ID, peerId);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        return intent;
    }
}
