package com.kaurihealth.chatlib.cache;

import android.content.Context;
import android.text.TextUtils;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.kaurihealth.chatlib.event.LCIMOfflineMessageCountChangeEvent;
import com.kaurihealth.chatlib.handler.LCIMMessageHandler;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by wli on 16/2/26.
 * 缓存未读消息数量
 * <p/>
 * 流程
 * 1、初始化时从 db 里同步数据到缓存
 * 2、插入数据时先更新缓存，在更新 db
 * 3、获取的话只从缓存里读取数据
 */
public class LCIMConversationItemCache {


    private Map<String, LCIMConversationItem> conversationItemMap;
    private LCIMLocalStorage conversationItemDBHelper;

    public static LCIMConversationItemCache getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {

        static final LCIMConversationItemCache INSTANCE = new LCIMConversationItemCache();
    }

    private LCIMConversationItemCache() {
        conversationItemMap = new HashMap<>();
    }

    /**
     * 第一次的时候需要设置 Context 以及 clientId，根据syncData()--getIds()
     * 因为需要同步数据，所以此处需要有回调
     *
     * @param context
     * @param clientId 我的id
     * @param callback
     */
    public synchronized void initDB(Context context, String clientId, AVCallback callback) {
        String CONVERSATION_ITEM_TABLE_NAME = "ConversationItem_4";
        conversationItemDBHelper = new LCIMLocalStorage(context, clientId, CONVERSATION_ITEM_TABLE_NAME);
        conversationItemMap.clear();
        syncData(callback);
    }

    /**
     * 同步 db 数据到内存中
     * 先获取到key idList
     * 再获取到conversationItem 同步到内存
     */
    private void syncData(final AVCallback callback) {
        conversationItemDBHelper.getIds(new AVCallback<List<String>>() {
            @Override
            protected void internalDone0(final List<String> idList, AVException e) {
                conversationItemDBHelper.getData(idList, new AVCallback<List<String>>() {
                    @Override
                    protected void internalDone0(final List<String> dataList, AVException e) {
                        if (null != dataList) {
                            for (int i = 0; i < dataList.size(); i++) {
                                LCIMConversationItem conversationItem = LCIMConversationItem.fromJsonString(dataList.get(i));
                                conversationItemMap.put(conversationItem.conversationId, conversationItem);
                            }
                            //TODO add+
                            EventBus.getDefault().postSticky(new LCIMOfflineMessageCountChangeEvent());
                        }
                        callback.internalDone(e);
                    }
                });
            }
        });
    }

    /**
     * 此处的消息未读数量仅仅指的是本机的未读消息数量，并没有存储到 server 端
     * 在收到消息时消息未读数量 + 1
     * {@link LCIMMessageHandler}
     *
     * @param convid 不能为空
     */
    public void increaseUnreadCount(String convid) {
        if (!TextUtils.isEmpty(convid)) {
            increaseUnreadCount(convid, 1);
        }
    }

    /**
     * 在原来的基础增加未读的消息数量
     *
     * @param convId    不能为空
     * @param increment 必须要大于 0  对应当前LCIMConversationItem-unreadCount增加
     */
    public synchronized void increaseUnreadCount(String convId, int increment) {
        if (!TextUtils.isEmpty(convId) && increment > 0) {
            LCIMConversationItem conversationItem = getConversationItemFromMap(convId);
            conversationItem.unreadCount += increment;
            syncToCache(conversationItem);
            //TODO add+
            EventBus.getDefault().post(new LCIMOfflineMessageCountChangeEvent());
        }
    }

    /**
     * 存储未读消息数量到内存及数据库
     */
    private void syncToCache(LCIMConversationItem item) {
        if (null != item&&conversationItemDBHelper!=null) {
            item.updateTime = System.currentTimeMillis();
            conversationItemMap.put(item.conversationId, item);
            conversationItemDBHelper.insertData(item.conversationId, item.toJsonString());
        }
    }

    /**
     * 缓存该 Conversastoin，默认未读数量为 0
     *
     * @param convId 不能为空
     */
    public synchronized void insertConversation(String convId) {
        if (!TextUtils.isEmpty(convId)) {
            syncToCache(getConversationItemFromMap(convId));
        }
    }

    /**
     * 清空未读的消息数量
     *
     * @param convid 不能为空
     */
    public synchronized void clearUnread(String convid) {
        if (!TextUtils.isEmpty(convid)) {
            LCIMConversationItem unreadCountItem = getConversationItemFromMap(convid);
            unreadCountItem.unreadCount = 0;
            syncToCache(unreadCountItem);
            //TODO delete --
            EventBus.getDefault().post(new LCIMOfflineMessageCountChangeEvent());//发送event事件，告知整体更新，可去掉未读数量
        }
    }

    /**
     * 删除该 Conversation 未读数量的缓存
     *
     * @param convid 不能为空
     */
    public synchronized void deleteConversation(String convid) {
        if (!TextUtils.isEmpty(convid)) {
            conversationItemMap.remove(convid);
            conversationItemDBHelper.deleteData(Arrays.asList(convid));
        }
    }

    /**
     * 获取该 Conversation 的未读数量
     *
     * @param convId 不能为空
     * @return
     */
    public synchronized int getUnreadCount(String convId) {
        return getConversationItemFromMap(convId).unreadCount;
    }

    /**
     * 获得排序后的 Conversation Id list，根据本地更新时间降序排列
     */
    public synchronized List<String> getSortedConversationList() {
        List<String> idList = new ArrayList<>();
        SortedSet<LCIMConversationItem> sortedSet = new TreeSet<>();//排序
        sortedSet.addAll(conversationItemMap.values());
        for (LCIMConversationItem item : sortedSet) {
            idList.add(item.conversationId);
        }
        return idList;
    }

    /**
     * 获取当前会话对象id记录bean
     */
    public synchronized List<LCIMConversationItem> getConversationList() {
        List<LCIMConversationItem> converList = new ArrayList<>();
        SortedSet<LCIMConversationItem> sortedSet = new TreeSet<>();
        sortedSet.addAll(conversationItemMap.values());
        for (LCIMConversationItem item : sortedSet) {
            converList.add(item);
        }
        return converList;
    }


    /**
     * 从 map 中获取 ConversationItem，如缓存中没有，则 new 一个新实例返回
     */
    private LCIMConversationItem getConversationItemFromMap(String convId) {
        if (conversationItemMap.containsKey(convId)) {
            return conversationItemMap.get(convId);
        }
        return new LCIMConversationItem(convId);
    }
}
