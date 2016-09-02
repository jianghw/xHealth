package com.kaurihealth.kaurihealth.chat.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.chatlibrary.adapter.ChatAdapter;
import com.example.chatlibrary.bean.ChatBean;
import com.example.chatlibrary.chat.ChatInterface;
import com.example.chatlibrary.chat.ChatUtils;
import com.example.chatlibrary.chat.ChatInjection;
import com.example.chatlibrary.chat.SingleChat;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.kaurihealth.datalib.request_bean.bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.request_bean.bean.PersonInfoBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.youyou.zllibrary.util.CommonActivity;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 张磊 on 2016/5/11.
 * 介绍：
 */
public class SingleChatActivity extends CommonActivity {
    @Bind(R.id.tvOtherName)
    TextView tvOtherName;
    @Bind(R.id.edtMesContent)
    EditText edtMesContent;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    private ChatUtils chatUtils;
    ChatInterface<ChatBean> chatInterface;

    private String kauriHealthId;
    private String fullName;
    private AVIMConversation avimConversation;
    private ChatBean other;
    List<AVIMMessage> messageList = new LinkedList<>();
    private ChatAdapter messageAdapter;
    private PersonInfoBean personInfoBean;
    private IGetter getter;
    private AVIMMessageHandler handler;

    {
        chatUtils = new ChatUtils();
        chatUtils.setChatInterface(new SingleChat(ChatUtils.avimClient, ChatUtils.control));
        chatInterface = chatUtils.getChatInterface();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_chat);
        ButterKnife.bind(this);

        init();
        setData();
    }

    @Override
    public void init() {
        super.init();
        getter = Getter.getInstance(getApplicationContext());
        logUtil = LogFactory.getSimpleLog(this.getClass().getName());

        personInfoBean = getter.getPersonInfo();
        kauriHealthId = getter.getKauriHealthId();
        fullName = getter.getFullName();

        final ChatBean self = new ChatBean(kauriHealthId, fullName, personInfoBean.avatar);

        Bundle bundle = getBundle();
        if (bundle != null) {
            DoctorPatientRelationshipBean bean = (DoctorPatientRelationshipBean) bundle.getSerializable("CurrentDoctorPatientRelationship");
            //表示从医患界面的 患者界面跳转过来
            if (bean != null) {
                other = new ChatBean(bean.patient.kauriHealthId, bean.patient.fullName, bean.patient.avatar);
            } else {//表示从消息界面跳转过来
                other = (ChatBean) bundle.getSerializable("data");
            }

            chatInterface.createConversation(self, other, new AVIMConversationCreatedCallback() {
                @Override
                public void done(AVIMConversation avimConversation, AVIMException e) {
                    if (e == null) {
                        logUtil.i(avimConversation.getConversationId());
                        SingleChatActivity.this.avimConversation = avimConversation;
                        logUtil.i("对话创建成功");
                        messageReceive(SingleChatActivity.this.avimConversation);
                        refreshData();
                    } else {
                        logUtil.e(e.getMessage());
//                        Bugtags.sendException(e);
                    }
                }
            });
        }
        messageAdapter = new ChatAdapter(getApplicationContext(), messageList, personInfoBean.avatar, other.getAvatar(), kauriHealthId);
        lvContent.setAdapter(messageAdapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    private void refreshData() {
        ChatInjection.getConversationHistory(SingleChatActivity.this.avimConversation, new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> list, AVIMException e) {
                if (e == null) {
                    messageList.clear();
                    messageList.addAll(list);
                    messageAdapter.notifyDataSetChanged();
                    lvContent.setSelection(messageAdapter.getCount() - 1);
                    logUtil.i("获取历史记录成功,历史记录数目：" + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        logUtil.i("第" + i + "条数据：" + list.get(i).getFrom());
                    }
                } else {
//                    Bugtags.sendException(e);
                    logUtil.i("获取历史记录失败");
                }
            }
        });
    }

    private void setData() {
        tvOtherName.setText(other.getFullName());
    }


    @OnClick(R.id.ivSendMessage)
    public void onClick() {
        final String message = edtMesContent.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            showToast("消息不能为空");
            return;
        }
        chatInterface.sendMessage(avimConversation, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    refreshData();
                    edtMesContent.setText("");
                    logUtil.i("发送消息成功");
                } else {
//                    Bugtags.sendException(e);
                    logUtil.e(e.getMessage());
                }
            }
        }, message);
    }

    /**
     * 接受到消息的处理逻辑
     */
    private void messageReceive(final AVIMConversation myConversation) {
        handler = new AVIMMessageHandler() {
            @Override
            public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
                if (myConversation.getConversationId().equals(conversation.getConversationId())) {
                    receiveMessageFromThis(message, conversation, client);
                } else {
                    receiveMessageFromOther(message, conversation, client);
                }
            }
        };
        ChatInjection.monitorMessage(handler);
    }

    /**
     * 收到这个对话的消息
     */
    private void receiveMessageFromThis(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
        logUtil.i("收到当前对话的消息    来自:" + message.getFrom());
        if (message instanceof AVIMTextMessage) {
            messageList.add(message);
            messageAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 收到其他对话的消息
     */
    private void receiveMessageFromOther(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
    }

    private void onBack() {
        if (avimConversation == null) {
            finishCur();
        } else {
            ChatInjection.setLastReadTimeOfConversation(avimConversation, new AVIMConversationCallback() {
                @Override
                public void done(AVIMException e) {
                    finishCur();
                }
            }, getter.getKauriHealthId());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
