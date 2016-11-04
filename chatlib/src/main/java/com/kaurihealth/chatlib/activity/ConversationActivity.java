package com.kaurihealth.chatlib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVCallback;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.R;
import com.kaurihealth.chatlib.adapter.LCIMChatAdapter;
import com.kaurihealth.chatlib.cache.LCChatKitUser;
import com.kaurihealth.chatlib.cache.LCChatProfileProvider;
import com.kaurihealth.chatlib.cache.LCChatProfilesCallBack;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.custom.LCIMInputBottomBar;
import com.kaurihealth.chatlib.event.LCIMIMTypeMessageEvent;
import com.kaurihealth.chatlib.event.LCIMInputBottomBarEvent;
import com.kaurihealth.chatlib.event.LCIMInputBottomBarRecordEvent;
import com.kaurihealth.chatlib.event.LCIMInputBottomBarTextEvent;
import com.kaurihealth.chatlib.event.LCIMMessageResendEvent;
import com.kaurihealth.chatlib.utils.LCIMAudioHelper;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.chatlib.utils.LCIMConversationUtils;
import com.kaurihealth.chatlib.utils.LCIMLogUtils;
import com.kaurihealth.chatlib.utils.LCIMNotificationUtils;
import com.kaurihealth.chatlib.utils.LCIMPathUtils;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianghw on 2016/10/10.
 * <p/>
 * 描述：
 */

public class ConversationActivity extends AppCompatActivity {

    private ScrollChildSwipeRefreshLayout mSwipeRefresh;
    private RecyclerView recyclerView;
    private LCIMChatAdapter itemAdapter;
    private ImageView backImageView;
    private TextView nameTitle;
    private LCIMInputBottomBar inputBottomBar;
    private LinearLayoutManager layoutManager;

    /**
     * 当前 AVIMConversation
     */
    private AVIMConversation mConversation;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    // 记录拍照等的文件路径
    protected String localCameraPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        inputBottomBar = (LCIMInputBottomBar) findViewById(R.id.lc_bottomBar);

        backImageView = (ImageView) findViewById(R.id.iv_back);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nameTitle = (TextView) findViewById(R.id.tv_title);
        mSwipeRefresh = (ScrollChildSwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mSwipeRefresh.setScrollUpChild(recyclerView);

        itemAdapter = new LCIMChatAdapter();
        itemAdapter.resetRecycledViewPoolSize(recyclerView);
        recyclerView.setAdapter(itemAdapter);

        EventBus.getDefault().register(this);

        initByIntent(getIntent());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mConversation) {
            LCIMNotificationUtils.addTag(mConversation.getConversationId());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LCIMAudioHelper.getInstance().stopPlayer();
        if (null != mConversation) {
            LCIMNotificationUtils.removeTag(mConversation.getConversationId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initByIntent(intent);
    }

    private void initByIntent(Intent intent) {
        if (null == LCChatKit.getInstance().getClient()) {
            finish();
            return;
        }

        Bundle extras = intent.getExtras();
        if (null != extras) {
            if (extras.containsKey(LCIMConstants.PEER_ID)) {
                getConversation(extras.getString(LCIMConstants.PEER_ID));
            } else if (extras.containsKey(LCIMConstants.CONVERSATION_ID)) {
                String conversationId = extras.getString(LCIMConstants.CONVERSATION_ID);
                updateConversation(LCChatKit.getInstance().getClient().getConversation(conversationId));
            } else {
                showToast("memberId or conversationId is needed");
                finish();
            }
        }
    }

    /**
     * 主动刷新 UI
     */
    protected void updateConversation(AVIMConversation conversation) {
        if (null != conversation) {
            setConversation(conversation);
//未读数减
            LCIMConversationItemCache.getInstance().clearUnread(conversation.getConversationId());
            LCIMConversationUtils.getConversationName(conversation, new AVCallback<String>() {
                @Override
                protected void internalDone0(String name, AVException e) {
                    if (null != e) {
                        LCIMLogUtils.logException(e);
                    } else {
                        initActionBar(name);
                    }
                }
            });
        }
    }

    /**
     * 设置conversation
     */
    public void setConversation(final AVIMConversation conversation) {
        mConversation = conversation;
        inputBottomBar.setTag(mConversation.getConversationId());
        fetchMessages();
//通知栏不弹出标记
        LCIMNotificationUtils.addTag(conversation.getConversationId());

        if (!conversation.isTransient()) {//不是暂态
            if (conversation.getMembers().size() == 0) {
                conversation.fetchInfoInBackground(new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        if (null != e) {
                            LCIMLogUtils.logException(e);
                        }
                        itemAdapter.showUserName(conversation.getMembers().size() > 2);
                    }
                });
            } else {
                itemAdapter.showUserName(conversation.getMembers().size() > 2);
            }
        } else {
            itemAdapter.showUserName(true);
        }
    }

    /**
     * 更新标题
     *
     * @param name
     */
    private void initActionBar(String name) {
        nameTitle.setText(name != null ? name : "未知");
    }

    /**
     * 初始化获取 id-->conversation  创建对话
     * <p>
     * 为了避免重复的创建，createConversation 参数 isUnique 设为 true·
     * members - 对话的初始成员列表。在对话创建成功后，这些成员会收到和邀请加入对话一样的相应通知。
     * name - 对话的名字，主要是用于标记对话，让用户更好地识别对话。
     * attributes - 额外属性
     * isTransient - 是否为 暂态对话
     * isUnique - 是否创建唯一对话，当 isUnique 为 true 时，如果当前已经有相同成员的对话存在则返回该对话，否则会创建新的对话。该值默认为 false。
     */
    protected void getConversation(final String memberId) {
        AVIMClient avimClient = LCChatKit.getInstance().getClient();
        if (avimClient != null) {
            getLCChatKitUser(memberId, avimClient);
        }
    }

    private void getLCChatKitUser(final String memberId, final AVIMClient avimClient) {
        LCChatProfileProvider profileProvider = LCChatKit.getInstance().getProfileProvider();
        if (null != profileProvider) {
            profileProvider.fetchProfiles(Arrays.asList(memberId), new LCChatProfilesCallBack() {
                @Override
                public void done(List<ContactUserDisplayBean> userList, Exception e) {
                    LogUtils.jsonDate(userList);
                    if (null != userList && userList.size() > 0) {
                        LCChatKitUser[] members = new LCChatKitUser[2];
                        ContactUserDisplayBean contactUserDisplayBean = userList.get(0);
                        members[0] = new LCChatKitUser(contactUserDisplayBean.getKauriHealthId(), contactUserDisplayBean.getFullName(), contactUserDisplayBean.getAvatar());
                        LogUtils.jsonDate(userList.get(0));
                        UserBean userBean = LocalData.getLocalData().getTokenBean().getUser();
                        members[1] = new LCChatKitUser(userBean.getKauriHealthId(), userBean.getFullName(), userBean.getAvatar());
                        Map<String, Object> attr = new HashMap<>();
                        attr.put("members", members);
                        createConversation(attr, avimClient, memberId);
                    }
                }
            });
        }
    }

    /**
     * 创建对话
     */
    private void createConversation(Map<String, Object> attr, AVIMClient avimClient, String memberId) {
        avimClient.createConversation(
                Arrays.asList(memberId), "", attr, false, true, new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation avimConversation, AVIMException e) {
                        if (null != e) {
                            showToast(e.getMessage());
                        } else {
                            updateConversation(avimConversation);
                        }
                    }
                });
    }

    /**
     * 拉取消息，必须加入 conversation 后才能拉取消息
     * 查询数据
     */
    private void fetchMessages() {
        mConversation.queryMessages(new AVIMMessagesQueryCallback() {
            @Override
            public void done(List<AVIMMessage> messageList, AVIMException e) {
                if (filterException(e)) {
                    itemAdapter.setMessageList(messageList);
                    recyclerView.setAdapter(itemAdapter);
                    itemAdapter.notifyDataSetChanged();
                    scrollToBottom();
                }
            }
        });
    }

    /**
     * 弹出 toast
     *
     * @param content
     */
    private void showToast(String content) {
        Toast.makeText(ConversationActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    private boolean filterException(Exception e) {
        if (null != e) {
            LCIMLogUtils.logException(e);
            showToast(e.getMessage());
        }
        return (null == e);
    }

    /**
     * 滚动 recyclerView 到底部
     */
    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
    }

    /**
     * 处理推送过来的消息
     * 同理，避免无效消息，此处加了 conversation id 判断
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMIMTypeMessageEvent messageEvent) {
        if (null != mConversation && null != messageEvent &&
                mConversation.getConversationId().equals(messageEvent.conversation.getConversationId())) {
            itemAdapter.addMessage(messageEvent.message);
            itemAdapter.notifyDataSetChanged();
            scrollToBottom();
        }
    }

    /**
     * 处理输入栏发送过来的事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMInputBottomBarEvent event) {
        if (null != mConversation && null != event && mConversation.getConversationId().equals(event.tag)) {
            switch (event.eventAction) {
                case LCIMInputBottomBarEvent.INPUTBOTTOMBAR_IMAGE_ACTION://0
                    dispatchPickPictureIntent();
                    break;
                case LCIMInputBottomBarEvent.INPUTBOTTOMBAR_CAMERA_ACTION://1
                    dispatchTakePictureIntent();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 发送 Intent 跳转到系统图库页面
     */
    private void dispatchPickPictureIntent() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, null);
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(photoPickerIntent, REQUEST_IMAGE_PICK);
    }

    /**
     * 发送 Intent 跳转到系统拍照页面
     */
    private void dispatchTakePictureIntent() {
        localCameraPath = LCIMPathUtils.getPicturePathByCurrentTime(this.getApplicationContext());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(new File(localCameraPath));
        takePictureIntent.putExtra("return-data", false);
        takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (Activity.RESULT_OK == resultCode) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    sendImage(localCameraPath);
                    break;
                case REQUEST_IMAGE_PICK:
                    sendImage(getRealPathFromURI(this, data.getData()));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 根据 Uri 获取文件所在的位置
     *
     * @param context
     * @param contentUri
     * @return
     */
    private String getRealPathFromURI(Context context, Uri contentUri) {
        if (contentUri.getScheme().equals("file")) {
            return contentUri.getEncodedPath();
        } else {
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
                if (null != cursor) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                } else {
                    return "";
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    /**
     * 发送图片消息
     * TODO 上传的图片最好要压缩一下
     *
     * @param imagePath
     */
    protected void sendImage(String imagePath) {
        try {
            AVIMImageMessage message = new AVIMImageMessage(imagePath);
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("username", nameTitle.getText().toString().trim());
            message.setAttrs(attributes);
            sendMessage(message);
        } catch (IOException e) {
            LCIMLogUtils.logException(e);
        }
    }

    /**
     * 输入事件处理，接收后构造成 AVIMTextMessage 然后发送
     * 因为不排除某些特殊情况会受到其他页面过来的无效消息，所以此处加了 tag 判断
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMInputBottomBarTextEvent textEvent) {
        if (null != mConversation && null != textEvent) {
            if (!TextUtils.isEmpty(textEvent.sendContent) && mConversation.getConversationId().equals(textEvent.tag)) {
                sendText(textEvent.sendContent);
            }
        }
    }

    /**
     * 发送文本消息
     *
     * @param content
     */
    protected void sendText(String content) {
        AVIMTextMessage message = new AVIMTextMessage();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("username", nameTitle.getText().toString().trim());
        message.setAttrs(attributes);

        message.setText(content);
        sendMessage(message);
    }

    public void sendMessage(AVIMMessage message) {
        sendMessage(message, true);
    }

    /**
     * 处理录音事件
     *
     * @param recordEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMInputBottomBarRecordEvent recordEvent) {
        if (null != mConversation && null != recordEvent &&
                !TextUtils.isEmpty(recordEvent.audioPath) &&
                mConversation.getConversationId().equals(recordEvent.tag)) {
            sendAudio(recordEvent.audioPath);
        }
    }

    /**
     * 发送语音消息
     *
     * @param audioPath
     */
    protected void sendAudio(String audioPath) {
        try {
            AVIMAudioMessage audioMessage = new AVIMAudioMessage(audioPath);
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("username", nameTitle.getText().toString().trim());
            audioMessage.setAttrs(attributes);
            sendMessage(audioMessage);
        } catch (IOException e) {
            LCIMLogUtils.logException(e);
        }
    }

    /**
     * 重新发送已经发送失败的消息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMMessageResendEvent resendEvent) {
        if (null != mConversation && null != resendEvent &
                null != resendEvent.getMessage() && mConversation.getConversationId().equals(resendEvent.getMessage().getConversationId())) {
            if (AVIMMessage.AVIMMessageStatus.AVIMMessageStatusFailed == resendEvent.getMessage().getMessageStatus()
                    && mConversation.getConversationId().equals(resendEvent.getMessage().getConversationId())) {
                sendMessage(resendEvent.getMessage(), false);
            }
        }
    }

    /**
     * 发送消息
     *
     * @param message
     */
    public void sendMessage(AVIMMessage message, boolean addToList) {
        if (addToList) {
            itemAdapter.addMessage(message);
        }
        itemAdapter.notifyDataSetChanged();
        scrollToBottom();
        mConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                itemAdapter.notifyDataSetChanged();
                if (null != e) {
                    LogUtils.e(e.getMessage());
                    e.printStackTrace();
                    LCIMLogUtils.logException(e);
                }
            }
        });
    }


}
