package com.kaurihealth.chatlib.viewholder;


import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMSingleMessageQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.kaurihealth.chatlib.R;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.custom.LCChatMessageInterface;
import com.kaurihealth.chatlib.event.LCIMConversationItemLongClickEvent;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.chatlib.utils.LCIMConversationUtils;
import com.kaurihealth.chatlib.utils.LCIMLogUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by wli on 15/10/8.
 * 会话 item 对应的 holder
 */
public class LCIMConversationItemHolder extends LCIMCommonViewHolder {

    CircleImageView avatarView;
    TextView unreadView;
    TextView messageView;
    TextView timeView;
    TextView nameView;

    public static ViewHolderCreator HOLDER_CREATOR = new ViewHolderCreator<LCIMConversationItemHolder>() {
        @Override
        public LCIMConversationItemHolder createByViewGroupAndType(ViewGroup parent, int viewType) {
            return new LCIMConversationItemHolder(parent);
        }
    };

    public LCIMConversationItemHolder(ViewGroup root) {
        super(root.getContext(), root, R.layout.rv_conversation_item);
        initView();
    }

    private void initView() {
        avatarView = (CircleImageView) itemView.findViewById(R.id.civ_head);
        nameView = (TextView) itemView.findViewById(R.id.tv_name);
        timeView = (TextView) itemView.findViewById(R.id.tv_time);
        unreadView = (TextView) itemView.findViewById(R.id.tv_unread);
        messageView = (TextView) itemView.findViewById(R.id.tv_message);
    }

    @Override
    public void bindData(Object o) {
        reset();
        final AVIMConversation conversation = (AVIMConversation) o;
        if (null != conversation) {
            if (null == conversation.getCreatedAt()) {
                conversation.fetchInfoInBackground(new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        if (e != null) {
                            LCIMLogUtils.logException(e);
                        } else {
                            updateName(conversation);
                            updateIcon(conversation);
                        }
                    }
                });
            } else {
                updateName(conversation);
                updateIcon(conversation);
            }
//未读数量
            updateUnreadCount(conversation);
//最后一条消息
            updateLastMessageByConversation(conversation);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onConversationItemClick(conversation);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setItems(new String[]{"删除该聊天"}, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            EventBus.getDefault().post(new LCIMConversationItemLongClickEvent(conversation));
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return false;
                }
            });
        }
    }

    /**
     * 一开始的时候全部置为空，避免因为异步请求造成的刷新不及时而导致的展示原有的缓存数据
     */
    private void reset() {
        Picasso.with(getContext()).load(R.mipmap.ic_circle_head_green).fit().into(avatarView);
        nameView.setText("");
        timeView.setText("");
        messageView.setText("");
        unreadView.setVisibility(View.GONE);
    }

    /**
     * 更新 name，单聊的话展示对方姓名，群聊展示所有用户的用户名
     *
     * @param conversation
     */
    private void updateName(AVIMConversation conversation) {
        LCIMConversationUtils.getConversationName(conversation, new AVCallback<String>() {
            @Override
            protected void internalDone0(String s, AVException e) {
                if (null != e) {
                    LCIMLogUtils.logException(e);
                } else {
                    nameView.setText(s);
                }
            }
        });
    }

    /**
     * 更新 item icon，目前的逻辑为：
     * 单聊：展示对方的头像
     * 群聊：展示一个静态的 icon
     *
     * @param conversation
     */
    private void updateIcon(AVIMConversation conversation) {
        if (null != conversation) {
            if (conversation.isTransient() || conversation.getMembers().size() > 2) {
                //TODO
            } else {
//                try {
//                    Object object = conversation.getAttribute("members");
//                    String attributeStr = object.toString();
//                    LogUtils.e(attributeStr);
//                    if (attributeStr != null&&!TextUtils.isEmpty(attributeStr)&&attributeStr.trim().length()>0) {
//                        List<LCChatKitUser> chatKitUserList = JSON.parseArray(attributeStr, LCChatKitUser.class);
//                        List<LCChatKitUser> chatKitUserList = new Gson().fromJson(attributeStr, new TypeToken<List<LCChatKitUser>>(){}.getType());
//                        LogUtils.jsonDate(chatKitUserList);
//                        for (LCChatKitUser user : chatKitUserList) {
//                            if (user != null && !user.getClientId().equals(LocalData.getLocalData().getTokenBean().getUser().getKauriHealthId())) {
//                                if (CheckUtils.checkUrlNotNull(user.getAvatar())) {
//                                    Picasso.with(getContext()).load(user.getAvatar()).fit().into(avatarView);
//                                }
//                            }
//                        }
//                    }
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }

                LCIMConversationUtils.getConversationPeerIcon(conversation, new AVCallback<String>() {
                    @Override
                    protected void internalDone0(String url, AVException e) {
                        ImageUrlUtils.picassoBySmallUrlCircle(getContext(), url, avatarView);
                        if (null != e) {
                            LCIMLogUtils.logException(e);
                        }
                    }
                });
            }
        }
    }

    /**
     * 更新未读消息数量
     *
     * @param conversation
     */

    private void updateUnreadCount(AVIMConversation conversation) {
        int num = LCIMConversationItemCache.getInstance().getUnreadCount(conversation.getConversationId());
        unreadView.setText(num + "");
        unreadView.setVisibility(num > 0 ? View.VISIBLE : View.GONE);
    }

    /**
     * 更新最后一条消息
     * queryMessages
     *
     * @param conversation
     */
    private void updateLastMessageByConversation(final AVIMConversation conversation) {
        // TODO 此处如果调用 AVIMConversation.getLastMessage 的话会造成一直读取缓存数据造成展示不对
        // 所以使用 queryMessages，但是这个接口还是很难有，需要 sdk 对这个进行支持
        conversation.getLastMessage(new AVIMSingleMessageQueryCallback() {
            @Override
            public void done(AVIMMessage avimMessage, AVIMException e) {
                if (null != avimMessage) {
                    updateLastMessage(avimMessage);
                } else {
                    int limit = 1;
                    conversation.queryMessages(limit, new AVIMMessagesQueryCallback() {
                        @Override
                        public void done(List<AVIMMessage> list, AVIMException e) {
                            if (null != e) {
                                LCIMLogUtils.logException(e);
                            }
                            if (null != list && !list.isEmpty()) {
                                updateLastMessage(list.get(0));
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 更新 item 的展示内容，及最后一条消息的内容
     *
     * @param message
     */
    private void updateLastMessage(AVIMMessage message) {
        if (null != message) {
            Date date = new Date(message.getTimestamp());
            SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm", Locale.SIMPLIFIED_CHINESE);
            timeView.setText(format.format(date));
            messageView.setText(getMessageShorthand(getContext(), message));
        }
    }

    private static CharSequence getMessageShorthand(Context context, AVIMMessage message) {
        if (message instanceof AVIMTypedMessage) {
            AVIMReservedMessageType type = AVIMReservedMessageType.getAVIMReservedMessageType(
                    ((AVIMTypedMessage) message).getMessageType());
            switch (type) {
                case TextMessageType:
                    return ((AVIMTextMessage) message).getText();
                case ImageMessageType:
                    return context.getString(R.string.lcim_message_shorthand_image);
                case LocationMessageType:
                    return context.getString(R.string.lcim_message_shorthand_location);
                case AudioMessageType:
                    return context.getString(R.string.lcim_message_shorthand_audio);
                default:
                    CharSequence shortHand = "";
                    if (message instanceof LCChatMessageInterface) {
                        LCChatMessageInterface messageInterface = (LCChatMessageInterface) message;
                        shortHand = messageInterface.getShorthand();
                    }
                    if (TextUtils.isEmpty(shortHand)) {
                        shortHand = context.getString(R.string.lcim_message_shorthand_unknown);
                    }
                    return shortHand;
            }
        } else {
            return message.getContent();
        }
    }

    /**
     * 跳转页面
     *
     * @param conversation
     */
    private void onConversationItemClick(AVIMConversation conversation) {
        try {
            Intent intent = new Intent();
            intent.setPackage(getContext().getPackageName());
            intent.setAction(LCIMConstants.CONVERSATION_ITEM_CLICK_ACTION);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(LCIMConstants.CONVERSATION_ID, conversation.getConversationId());
            getContext().startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Log.i(LCIMConstants.LCIM_LOG_TAG, exception.toString());
        }
    }
}
