package com.example.chatlibrary.chat;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.example.chatlibrary.constant.LeanCloud;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 * 改：jianghw  聊天注入
 */
public class ChatInjection {

    public static AVIMClient avimClient;//连接后，赋值
    /**
     * 默认获取对话数目
     */
    private static final int RecentConversationCount = 100;
    /**
     * 默认获取某条对话获取聊天记录数目
     */
    private static final int DefaultConversationHistoryCount = 1000;
    public static ChatIsAvailableInterface control = new ChatIsAvailableInterface() {

        @Override
        public boolean isAvailable() {
            return avimClient != null;
        }
    };

    /**
     * 初始化LeanCloud
     *
     * @param context
     */
    public static void init(Context context, String environment) {
        switch (environment) {
            case "Develop":
            case "Test":
                AVOSCloud.initialize(context, LeanCloud.Config.APP_ID_DEBUG, LeanCloud.Config.APP_KEY_DEBUG);
                break;
            case "Preview":
                AVOSCloud.initialize(context, LeanCloud.Config.APP_ID_PREVIEW, LeanCloud.Config.APP_KEY_PREVIEW);
                break;
            default:
                AVOSCloud.initialize(context, LeanCloud.Config.APP_ID_PREVIEW, LeanCloud.Config.APP_KEY_PREVIEW);
                break;
        }

    }

    /**
     * 连接服务器
     */
    /**
     *
     * @param account 对话发起人
     * @param avimClientCallback
     */
    public static void chatConnect(String account, AVIMClientCallback avimClientCallback) {
        AVIMClient client = AVIMClient.getInstance(account);
        client.open(avimClientCallback);
    }

    /**
     * 获得所有的对话
     */
    public static void getAllConversation(AVIMConversationQueryCallback queryCallback) {
        getAllConversation(queryCallback, RecentConversationCount);
    }

    public static void getAllConversation(AVIMConversationQueryCallback queryCallback, int limit) {
        if (!(control.isAvailable())) {
            return;
        }
        AVIMConversationQuery query = avimClient.getQuery();
        query.setQueryPolicy(AVQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.limit(limit);
        query.findInBackground(queryCallback);
    }

    /**
     * 获得对话历史记录
     */
    public static void getConversationHistory(AVIMConversation conversation, AVIMMessagesQueryCallback queryCallback, int limit) {
        if (!(control.isAvailable())) {
            return;
        }
        conversation.queryMessages(limit, queryCallback);
    }

    public static void getConversationHistory(AVIMConversation conversation, AVIMMessagesQueryCallback queryCallback) {
        if (!(control.isAvailable())) {
            return;
        }
        conversation.queryMessages(DefaultConversationHistoryCount, queryCallback);
    }

    /**
     * 监听消息
     */
    public static void monitorMessage(AVIMMessageHandler handler) {
        AVIMMessageManager.registerDefaultMessageHandler(handler);
    }


    private static String LastReadTime = "LastReadTime";

    /**
     * 设置对话最后阅读时间
     */
    public static void setLastReadTimeOfConversation(AVIMConversation avimConversation, AVIMConversationCallback callback, String kauriHealthId) {
        Object attribute = avimConversation.getAttribute(LastReadTime);
        long curTime = Calendar.getInstance().getTime().getTime();
        if (attribute != null) {
            MesTimeBean[] mesTimeBeens = (MesTimeBean[]) attribute;
            int i = 0;
            for (; i < mesTimeBeens.length; i++) {
                if (mesTimeBeens[i].kaurihealthId == kauriHealthId) {
                    mesTimeBeens[i].lastReadTime = curTime;
                }
            }
            if (i == mesTimeBeens.length) {
                MesTimeBean[] mesBeen = new MesTimeBean[mesTimeBeens.length + 1];
                for (int j = 0; j < mesTimeBeens.length; j++) {
                    mesBeen[j] = mesTimeBeens[j];
                }
                mesBeen[mesBeen.length - 1] = new MesTimeBean(kauriHealthId, curTime);
            }
            avimConversation.setAttribute(LastReadTime, mesTimeBeens);
        } else {
            MesTimeBean[] mesTimeBeen = {new MesTimeBean(kauriHealthId, curTime)};
            avimConversation.setAttribute(LastReadTime, mesTimeBeen);
        }
        avimConversation.updateInfoInBackground(callback);
    }

    /**
     * 判断对话是否阅读过
     */
    public static void hasReadConversation(AVIMConversation avimConversation, final SuccessInterfaceM<Boolean> successInterfaceM, String kauriHealthId) {
        if (avimConversation == null) {
            return;
        }
        long lastReadTime = 0;
        Object attribute = avimConversation.getAttribute(LastReadTime);
        if (attribute != null) {
            List<MesTimeBean> mesTimeBeen = JSON.parseArray(attribute.toString(), MesTimeBean.class);
            int i = 0;
            for (; i < mesTimeBeen.size(); i++) {
                if (mesTimeBeen.get(i).kaurihealthId.equals(kauriHealthId)) {
                    lastReadTime = mesTimeBeen.get(i).lastReadTime;
                    break;
                }
            }
            if (i == mesTimeBeen.size()) {
                lastReadTime = 0;
            }
        } else {
            lastReadTime = 0;
        }
        final long lastReadTimeFinal = lastReadTime;
        avimConversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
            @Override
            public void done(AVIMMessage avimMessage, AVIMException e) {
                if (e == null && avimMessage != null) {
                    successInterfaceM.success(lastReadTimeFinal > avimMessage.getTimestamp());
                } else {
                    successInterfaceM.success(false);
                }
            }
        });
    }

    static class MesTimeBean implements Serializable {
        public String kaurihealthId;
        public long lastReadTime;

        public MesTimeBean(String kaurihealthId, long lastReadTime) {
            this.kaurihealthId = kaurihealthId;
            this.lastReadTime = lastReadTime;
        }

        public MesTimeBean() {
        }
    }
}
