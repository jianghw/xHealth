package com.kaurihealth.chatlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.viewholder.LCIMChatItemAudioHolder;
import com.kaurihealth.chatlib.viewholder.LCIMChatItemHihtTextHolder;
import com.kaurihealth.chatlib.viewholder.LCIMChatItemHolder;
import com.kaurihealth.chatlib.viewholder.LCIMChatItemImageHolder;
import com.kaurihealth.chatlib.viewholder.LCIMChatItemSickCardHolder;
import com.kaurihealth.chatlib.viewholder.LCIMChatItemTextHolder;
import com.kaurihealth.chatlib.viewholder.LCIMCommonViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Created by wli on 15/8/13.
 * 聊天的 Adapter，此处还有可优化的地方，稍后考虑一下提取出公共的 adapter
 */
public class LCIMChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_LEFT = 100;
    private final int ITEM_LEFT_TEXT = 101;
    private final int ITEM_LEFT_IMAGE = 102;
    private final int ITEM_LEFT_AUDIO = 103;
    private final int ITEM_LEFT_LOCATION = 104;

    private final int ITEM_RIGHT = 200;
    private final int ITEM_RIGHT_TEXT = 201;
    private final int ITEM_RIGHT_IMAGE = 202;
    private final int ITEM_RIGHT_AUDIO = 203;
    private final int ITEM_RIGHT_LOCATION = 204;

    private final int ITEM_UNKNOWN = 300;

    private final int ITEM_HIHT_TV = 400;

    private final int ITEM_LEFT_CARD_SINCK = 10;
    private final int ITEM_RIGHT_CARD_SINCK = 20;

    // 时间间隔最小为十分钟
    private final static long TIME_INTERVAL = 1000 * 60 * 3;
    private boolean isShowUserName = true;
    private List<AVIMMessage> messageList = new ArrayList<>();

    public LCIMChatAdapter() {
        super();
    }

    /**
     * 塞进消息
     */
    public void setMessageList(List<AVIMMessage> messages) {
        messageList.clear();
        if (null != messages) {
            messageList.addAll(messages);
        }
    }

    /**
     * 添加多条消息记录
     *
     * @param messages
     */
    public void addMessageList(List<AVIMMessage> messages) {
        messageList.addAll(0, messages);
    }

    /**
     * 添加消息记录
     *
     * @param message
     */
    public void addMessage(AVIMMessage message) {
        messageList.addAll(Arrays.asList(message));
    }

    /**
     * 获取第一条消息记录，方便下拉时刷新数据
     *
     * @return
     */
    public AVIMMessage getFirstMessage() {
        if (null != messageList && messageList.size() > 0) {
            return messageList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_HIHT_TV://提示信息
                return new LCIMChatItemHihtTextHolder(parent.getContext(), parent, true);
            case ITEM_LEFT:
            case ITEM_LEFT_TEXT:
                return new LCIMChatItemTextHolder(parent.getContext(), parent, true);
            case ITEM_LEFT_IMAGE:
                return new LCIMChatItemImageHolder(parent.getContext(), parent, true);
            case ITEM_LEFT_AUDIO:
                return new LCIMChatItemAudioHolder(parent.getContext(), parent, true);
            case ITEM_LEFT_CARD_SINCK:
                return new LCIMChatItemSickCardHolder(parent.getContext(), parent, true);
            case ITEM_RIGHT:
            case ITEM_RIGHT_TEXT:
                return new LCIMChatItemTextHolder(parent.getContext(), parent, false);
            case ITEM_RIGHT_IMAGE:
                return new LCIMChatItemImageHolder(parent.getContext(), parent, false);
            case ITEM_RIGHT_AUDIO:
                return new LCIMChatItemAudioHolder(parent.getContext(), parent, false);
            case ITEM_RIGHT_CARD_SINCK:
                return new LCIMChatItemSickCardHolder(parent.getContext(), parent, false);
            default:
                return new LCIMChatItemTextHolder(parent.getContext(), parent, true);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (messageList.get(position) != null)
            ((LCIMCommonViewHolder) holder).bindData(messageList.get(position));
        if (holder instanceof LCIMChatItemHolder) {
            ((LCIMChatItemHolder) holder).showTimeView(shouldShowTime(position));
            ((LCIMChatItemHolder) holder).showUserName(isShowUserName);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int messageType = 0;
        AVIMMessage message = messageList.get(position);
        if (message instanceof AVIMTextMessage) {
            AVIMTextMessage textMessage = (AVIMTextMessage) message;
            Map<String, Object> map = textMessage.getAttrs();
            if (map != null) messageType = map.get("messageType") != null ? (int) map.get("messageType") : 0;
        }

        if (null != message && message instanceof AVIMTypedMessage) {
            AVIMTypedMessage typedMessage = (AVIMTypedMessage) message;
            boolean isMe = fromMe(typedMessage);

            if (typedMessage.getMessageType() == 400) {//提示信息
                return ITEM_HIHT_TV;
            } else if (messageType == 1) {
                return isMe ? ITEM_RIGHT_CARD_SINCK : ITEM_LEFT_CARD_SINCK;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.TextMessageType.getType()) {
                return isMe ? ITEM_RIGHT_TEXT : ITEM_LEFT_TEXT;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.AudioMessageType.getType()) {
                return isMe ? ITEM_RIGHT_AUDIO : ITEM_LEFT_AUDIO;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.ImageMessageType.getType()) {
                return isMe ? ITEM_RIGHT_IMAGE : ITEM_LEFT_IMAGE;
            } else if (typedMessage.getMessageType() == AVIMReservedMessageType.LocationMessageType.getType()) {
                return isMe ? ITEM_RIGHT_LOCATION : ITEM_LEFT_LOCATION;
            } else if (typedMessage.getMessageType() == 1) {//名片
                return isMe ? ITEM_RIGHT_CARD_SINCK : ITEM_LEFT_CARD_SINCK;
            } else {
                return isMe ? ITEM_RIGHT : ITEM_LEFT;
            }
        }
        return ITEM_UNKNOWN;
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    /**
     * item 是否应该展示时间
     *
     * @param position
     * @return
     */
    private boolean shouldShowTime(int position) {
        if (position == 0) {
            return true;
        }
        long lastTime = messageList.get(position - 1).getTimestamp();
        long curTime = messageList.get(position).getTimestamp();
        return curTime - lastTime > TIME_INTERVAL;
    }

    /**
     * item 是否展示用户名
     * 因为
     *
     * @param isShow
     */
    public void showUserName(boolean isShow) {
        isShowUserName = isShow;
    }


    /**
     * 因为 RecyclerView 中的 item 缓存默认最大为 5，造成会重复的 create item 而卡顿
     * 所以这里根据不同的类型设置不同的缓存值，经验值，不同 app 可以根据自己的场景进行更改
     */
    public void resetRecycledViewPoolSize(RecyclerView recyclerView) {
        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_LEFT_TEXT, 25);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_LEFT_IMAGE, 10);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_LEFT_AUDIO, 15);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_LEFT_CARD_SINCK, 10);

        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_RIGHT_TEXT, 25);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_RIGHT_IMAGE, 10);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_RIGHT_AUDIO, 15);
        recyclerView.getRecycledViewPool().setMaxRecycledViews(ITEM_RIGHT_CARD_SINCK, 10);
    }

    /**
     * 是不是当前用户发送的数据
     *
     * @param msg
     * @return
     */
    private boolean fromMe(AVIMTypedMessage msg) {
        String selfId = LCChatKit.getInstance().getCurrentUserId();
        return msg.getFrom() != null && msg.getFrom().equals(selfId);
    }
}