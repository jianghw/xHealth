package com.kaurihealth.kaurihealth.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bugtags.library.Bugtags;
import com.example.chatlibrary.bean.ChatBean;
import com.example.chatlibrary.chat.ChatUtils;
import com.example.chatlibrary.chat.ChatInjection;
import com.example.chatlibrary.chat.SingleChat;
import com.example.commonlibrary.widget.CircleImageView;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：
 */
public class MessageAdapter extends CommonAdapter<AVIMConversation> {
    SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.SIMPLIFIED_CHINESE);
    private String kauriHealthId;
    private final IGetter getter;
    private ChatUtils chatUtils = new ChatUtils();

    public MessageAdapter(Context context, List<AVIMConversation> list, String kauriHealthId) {
        super(context, list);
        this.kauriHealthId = kauriHealthId;
        getter = Getter.getInstance(context);
        receiveMessage();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final AVIMConversation curConversation = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.messageiteam, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        viewHolder = (ViewHolder) convertView.getTag();
        String name = getName(curConversation);
        viewHolder.tvName.setText(name);
        //设置对话时间
        curConversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
            @Override
            public void done(AVIMMessage avimMessage, AVIMException e) {
                if (e == null) {
                    if (avimMessage != null) {
                        Date date = new Date(avimMessage.getTimestamp());
                        viewHolder.tvTime.setText(format.format(date));
                    } else {
                        viewHolder.tvTime.setText(getLastTime(curConversation));
                    }
                } else {
                    viewHolder.tvTime.setText(getLastTime(curConversation));
                }
            }
        });
        if (position == list.size() - 1) {
            //显示
            viewHolder.tvLine.setVisibility(View.INVISIBLE);
        } else {
            //不显示
            viewHolder.tvLine.setVisibility(View.VISIBLE);
        }
        getLastMessage(curConversation, new AVIMSingleMessageQueryCallback() {
            @Override
            public void done(AVIMMessage avimMessage, AVIMException e) {
                if (e == null) {
                    if (avimMessage instanceof AVIMTextMessage) {
                        viewHolder.tvMessage.setText(((AVIMTextMessage) avimMessage).getText());
                    }
                } else {
                    Bugtags.sendException(e);
                }
            }
        });
        ChatInjection.hasReadConversation(curConversation, new SuccessInterfaceM<Boolean>() {
            @Override
            public void success(Boolean hasRead) {
                if (hasRead) {
                    viewHolder.ivDot.setVisibility(View.GONE);
                } else {
                    viewHolder.ivDot.setVisibility(View.VISIBLE);
                }
            }
        }, getter.getKauriHealthId());
        return convertView;
    }


    public static class ViewHolder {
        @Bind(R.id.tvLine)
        TextView tvLine;
        @Bind(R.id.ivPhoto)
        CircleImageView ivPhoto;
        @Bind(R.id.tvName)
        TextView tvName;
        @Bind(R.id.tvTime)
        TextView tvTime;
        @Bind(R.id.tvMessage)
        TextView tvMessage;
        @Bind(R.id.iv_hasread_homeiteam)
        ImageView ivDot;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    /**
     * 获取对话的名称
     */
    private String getName(AVIMConversation conversation) {
        String result = null;
        switch (judge(conversation)) {
            case CommonChat:
                result = getCommonChatName(conversation);
                break;
            case GroupChat:
                result = getGroupChatName(conversation);
                break;
        }
        return result;
    }

    /**
     * 获取对话最新的更新时间
     */
    private String getLastTime(AVIMConversation conversation) {
        Date updatedAt = conversation.getUpdatedAt();
        String result = format.format(updatedAt);
        return result;
    }

    /**
     * 获取对话最新的消息内容
     */
    private void getLastMessage(AVIMConversation conversation, AVIMSingleMessageQueryCallback callback) {
        conversation.getLastMessage(callback);
    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    /**
     * 普通的双人聊天的时候返回对话名称
     */
    private String getCommonChatName(AVIMConversation conversation) {
        Object object = conversation.getAttribute(SingleChat.AttributeKey);
        if (object instanceof JSONArray) {
            JSONArray attribute = (JSONArray) object;
            if (attribute != null && attribute.size() > 0) {
                List<ChatBean> chatBeen = new ArrayList<>();
                for (int i = 0; i < attribute.size(); i++) {
                    JSONObject one = (JSONObject) attribute.get(i);
                    chatBeen.add(new ChatBean(one.getString("clientId"), one.getString("fullName"), one.getString("avatar")));
                }
                for (int i = 0; i < chatBeen.size(); i++) {
                    if (!(chatBeen.get(i).getClientId().equals(kauriHealthId))) {
                        return chatBeen.get(i).getFullName();
                    }
                }
            }
        } else {
            List<ChatBean> chatBeen = JSON.parseArray(object.toString(), ChatBean.class);
            for (int i = 0; i < chatBeen.size(); i++) {
                if (!(chatBeen.get(i).getClientId().equals(kauriHealthId))) {
                    return chatBeen.get(i).getFullName();
                }
            }
        }
        return null;
    }

    /**
     * 群聊的时候，返回对话名称
     */
    private String getGroupChatName(AVIMConversation conversation) {
        return null;
    }

    /**
     * 判断是群聊还是普通的双人聊天   目前的判断标准是   是否成员是两人
     */
    public ChatType judge(AVIMConversation conversation) {
        if (conversation.getMembers().size() == 2) {
            return ChatType.CommonChat;
        } else {
            return ChatType.GroupChat;
        }
    }

    enum ChatType {
        CommonChat, GroupChat
    }


    /**
     *聊天时间排序
     * */
    public void sort() {
        chatUtils.getAllConversation(new AVIMConversationQueryCallback() {
            @Override
            public void done(List<AVIMConversation> list, AVIMException e) {
                if (e == null) {
                    MessageAdapter.this.list.clear();
                    MessageAdapter.this.list.addAll(list);

                    notifyDataSetChanged();

                } else {
                    logUtil.e(e.getMessage());
                }
            }
        });
    }

    /**
     * 启动消息监听功能
     */
    public void receiveMessage() {
        ChatInjection.monitorMessage(new AVIMMessageHandler() {
            boolean hasNew = false;
            @Override
            public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
                logUtil.i("我被触发了");
                int i = 0;
                for (; i < list.size(); i++) {
                    if (list.get(i).getConversationId().equals(conversation.getConversationId())) {

                        MessageAdapter.this.sort();

                        list.set(i, conversation);
                        hasNew = true;
                        break;
                    }
                }
                if (i == list.size()) {
                    list.add(conversation);
                    hasNew = true;
                }
                if (hasNew) {
                    listener.success(true);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private SuccessInterfaceM<Boolean> listener;

    public void setMessageComeListener(SuccessInterfaceM<Boolean> listener) {
        this.listener = listener;
    }
}
