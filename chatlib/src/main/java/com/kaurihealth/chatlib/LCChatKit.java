package com.kaurihealth.chatlib;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SignatureFactory;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.kaurihealth.chatlib.cache.LCChatProfileProvider;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.chatlib.handler.LCIMClientEventHandler;
import com.kaurihealth.chatlib.handler.LCIMConversationHandler;
import com.kaurihealth.chatlib.handler.LCIMMessageHandler;

/**
 * Created by wli on 16/2/2. LeanCloudChatKit 的管理类
 */
public final class LCChatKit {
    private LCChatProfileProvider profileProvider;
    private String currentUserId = "";

    public static LCChatKit getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final LCChatKit INSTANCE = new LCChatKit();
    }

    /**
     * 初始化 LeanCloudChatKit，此函数要在 Application 的 onCreate 中调用 @param context @param appId @param appKey
     */
    public void initKey(Context context, String appId, String appKey, Class<? extends Activity> cls) {
        if (TextUtils.isEmpty(appId)) throw new IllegalArgumentException("appId can not be empty!");
        if (TextUtils.isEmpty(appKey))
            throw new IllegalArgumentException("appKey can not be empty!");
        AVOSCloud.initialize(context, appId, appKey);
        /** 消息处理 handler registerDefaultMessageHandler 和 registerMessageHandler 当客户端收到一条消息的时候，
         * 会优先根据消息类型通知当前所有注册的对应类型的普通的 messageHandler,
         * 如果发现当前没有任何注册的普通的 messageHandler，
         * 才会去通知 defaultMessageHandler 在 AVIMMessageManager 中多次注册 defaultMessageHandler ，
         * 只有最后一次调用的才是有效的 通过 registerMessageHandler 注册的 AVIMMessageHandler，则是可以同存的 */
        AVIMClient.setMessageQueryCacheEnable(true);

        /*推送*/
        PushService.setDefaultPushCallback(context,cls);
        /*来从服务端取回未读消息*/
        AVIMClient.setOfflineMessagePush(true);
        /* 和 Conversation 相关的事件的 handler*/
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class, new LCIMMessageHandler(context));
        /* 与网络相关的 handler*/
        AVIMClient.setClientEventHandler(LCIMClientEventHandler.getInstance());
        /* 默认设置为离线消息仅推送数量*/
        AVIMMessageManager.setConversationEventHandler(LCIMConversationHandler.getInstance());
    }

    /**
     * 设置用户体系 @param profileProvider
     */
    public void setProfileProvider(LCChatProfileProvider profileProvider) {
        this.profileProvider = profileProvider;
    }

    /**
     * 获取当前的用户体系 @return
     */
    public LCChatProfileProvider getProfileProvider() {
        return profileProvider;
    }

    /**
     * 设置签名工厂
     *
     * @param signatureFactory
     */
    public void setSignatureFactory(SignatureFactory signatureFactory) {
        AVIMClient.setSignatureFactory(signatureFactory);
    }

    /**
     * 开启实时聊天
     *
     * @param userId   #kauriHealthId
     * @param callback
     */
    public void open(final String userId, final AVIMClientCallback callback) {
        if (TextUtils.isEmpty(userId)) {
            throw new IllegalArgumentException("userId can not be empty!");
        }
        if (null == callback) {
            throw new IllegalArgumentException("callback can not be null!");
        }

        AVIMClient.getInstance(userId).open(new AVIMClientCallback() {
            @Override
            public void done(final AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    currentUserId = userId;
                    //用户信息
                    LCIMProfileCache.getInstance().initDB(AVOSCloud.applicationContext, userId);
                    //会话信息
                    LCIMConversationItemCache.getInstance().initDB(AVOSCloud.applicationContext, userId, new AVCallback() {
                        @Override
                        protected void internalDone0(Object o, AVException xe) {
                            callback.internalDone(avimClient, xe);
                        }
                    });
                } else {
                    callback.internalDone(avimClient, e);
                }
            }
        });
    }

    /**
     * 关闭实时聊天
     *
     * @param callback
     */
    public void close(final AVIMClientCallback callback) {
        AVIMClient.getInstance(currentUserId).close(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                currentUserId = null;
                if (null != callback) {
                    callback.internalDone(avimClient, e);
                }
            }
        });
    }

    /**
     * 获取当前的实时聊天的用户
     */
    public String getCurrentUserId() {
        return currentUserId;
    }

    /**
     * 获取当前的 AVIMClient 实例
     */
    public AVIMClient getClient() {
        if (!TextUtils.isEmpty(currentUserId)) {
            return AVIMClient.getInstance(currentUserId);
        }
        return null;
    }
}
