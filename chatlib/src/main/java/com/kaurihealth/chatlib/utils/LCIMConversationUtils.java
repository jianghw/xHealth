package com.kaurihealth.chatlib.utils;

import android.text.TextUtils;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wli on 16/3/2.
 * 和 Conversation 相关的 Util 类
 */
public class LCIMConversationUtils {

    /**
     * 获取会话名称
     * 优先级：
     * 1、AVIMConersation name 属性
     * 2、单聊：对方用户名
     * 群聊：成员用户名合并
     */
    public static void getConversationName(final AVIMConversation conversation, final AVCallback<String> callback) {
        if (null == callback) {
            return;
        }
        if (null == conversation) {
            callback.internalDone(null, new AVException(new Throwable("conversation can not be null!")));
            return;
        }
        if (conversation.isTransient()) {//暂态
            callback.internalDone(conversation.getName(), null);
        } else if (2 == conversation.getMembers().size()) {
            String peerId = getConversationPeerId(conversation);
            LCIMProfileCache.getInstance().getUserName(peerId, callback);
        } else {
            if (!TextUtils.isEmpty(conversation.getName())) {
                callback.internalDone(conversation.getName(), null);
            } else {
                LCIMProfileCache.getInstance().getCachedUsers(conversation.getMembers(), new AVCallback<List<ContactUserDisplayBean>>() {
                    @Override
                    protected void internalDone0(List<ContactUserDisplayBean> kitUserList, AVException e) {
                        List<String> nameList = new ArrayList<String>();
                        if (null != kitUserList) {
                            for (ContactUserDisplayBean userProfile : kitUserList) {
                                nameList.add(userProfile.getFullName());
                            }
                        }
                        callback.internalDone(TextUtils.join(",", nameList), e);
                    }
                });
            }
        }
    }

    /**
     * 获取单聊会话的 icon
     * 单聊：对方用户的头像
     * 群聊：返回 null
     *
     * @param conversation
     * @param callback
     */
    public static void getConversationPeerIcon(final AVIMConversation conversation, AVCallback<String> callback) {
        if (null != conversation && !conversation.isTransient() && !conversation.getMembers().isEmpty()) {
            String peerId = getConversationPeerId(conversation);
            if (1 == conversation.getMembers().size()) {
                peerId = conversation.getMembers().get(0);
            }
            LCIMProfileCache.getInstance().getUserAvatar(peerId, callback);
        } else {
            callback.internalDone(null, new AVException(new Throwable("cannot find icon!")));
        }
    }

    /**
     * 获取 “对方” 的用户 id，只对单聊有效，群聊返回空字符串
     *
     * @param conversation
     * @return
     */
    private static String getConversationPeerId(AVIMConversation conversation) {
        if (null != conversation && 2 == conversation.getMembers().size()) {
            String currentUserId = LCChatKit.getInstance().getCurrentUserId();
            String firstMemeberId = conversation.getMembers().get(0);
            return conversation.getMembers().get(firstMemeberId.equals(currentUserId) ? 1 : 0);
        }
        return "";
    }
}