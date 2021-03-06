package com.kaurihealth.chatlib.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.kaurihealth.chatlib.cache.LCIMProfileCache;
import com.kaurihealth.chatlib.custom.LCIMInputBottomBar;
import com.kaurihealth.chatlib.event.LCIMIMJoinedMessageEvent;
import com.kaurihealth.chatlib.event.LCIMIMKickedMessageEvent;
import com.kaurihealth.chatlib.event.LCIMIMTypeMessageEvent;
import com.kaurihealth.chatlib.event.LCIMInputBottomBarEvent;
import com.kaurihealth.chatlib.event.LCIMInputBottomBarRecordEvent;
import com.kaurihealth.chatlib.event.LCIMInputBottomBarTextEvent;
import com.kaurihealth.chatlib.event.LCIMMessageResendEvent;
import com.kaurihealth.chatlib.event.ReferralPatientEvent;
import com.kaurihealth.chatlib.event.SickCardMessageEvent;
import com.kaurihealth.chatlib.utils.JoinTypeMessage;
import com.kaurihealth.chatlib.utils.LCIMAudioHelper;
import com.kaurihealth.chatlib.utils.LCIMConstants;
import com.kaurihealth.chatlib.utils.LCIMConversationUtils;
import com.kaurihealth.chatlib.utils.LCIMLogUtils;
import com.kaurihealth.chatlib.utils.LCIMNotificationUtils;
import com.kaurihealth.chatlib.utils.LCIMPathUtils;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.datalib.response_bean.OnlineMainPagePatientDisplayBean;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.utilslib.ColorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.log.LogUtils;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianghw on 2016/10/10.
 * <p/>
 * 描述：
 */

public class GroupConversationActivity extends AppCompatActivity implements View.OnClickListener {

    private ScrollChildSwipeRefreshLayout mSwipeRefresh;
    private RecyclerView recyclerView;
    private LCIMChatAdapter itemAdapter;
    private ImageView backImageView;
    private TextView nameTitle;
    private LCIMInputBottomBar inputBottomBar;
    private LinearLayoutManager layoutManager;

    private ImageView detailsImageView;

    /**
     * 当前 AVIMConversation
     */
    private AVIMConversation mConversation;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private static final int REQUEST_IMAGE_PICK = 2;
    // 记录拍照等的文件路径
    protected String localCameraPath;
    private int Conversation_Type = 0;//1--群聊

    /**
     * 标记手动 添加消息通知
     */
    private boolean isSendCard = false;
    private MainPagePatientDisplayBean mDisplayBean;
    private boolean isCreated = false;
    private ArrayList<String> kauriHealthId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        inputBottomBar = (LCIMInputBottomBar) findViewById(R.id.lc_bottomBar);

        backImageView = (ImageView) findViewById(R.id.iv_back);
        detailsImageView = (ImageView) findViewById(R.id.iv_details);
        detailsImageView.setOnClickListener(this);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        nameTitle = (TextView) findViewById(R.id.tv_title);

        mSwipeRefresh = (ScrollChildSwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefresh.setColorSchemeColors(
                ColorUtils.setSwipeRefreshColors(this)
        );
        mSwipeRefresh.setDistanceToTriggerSync(Global.Numerical.SWIPE_REFRESH);

        recyclerView = (RecyclerView) findViewById(R.id.rv_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mSwipeRefresh.setScrollUpChild(recyclerView);

        itemAdapter = new LCIMChatAdapter();
        itemAdapter.resetRecycledViewPoolSize(recyclerView);
        recyclerView.setAdapter(itemAdapter);

        mSwipeRefresh.setEnabled(false);

        inputBottomBar.setReferralBtnGone(true);

        initByIntent(getIntent());

        EventBus.getDefault().register(this);
    }

    static class SwipeRefreshHandler extends Handler {
        private WeakReference<GroupConversationActivity> weakReference = null;

        SwipeRefreshHandler(GroupConversationActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            GroupConversationActivity activity = weakReference.get();
            if (null == weakReference || null == activity) return;
            activity.loadingIndicator(false);
        }
    }

    public void loadingIndicator(final boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefresh.setRefreshing(flag);
            }
        });
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
    protected void onStop() {
        super.onStop();
        removeStickyEvent(LCIMIMJoinedMessageEvent.class);
        removeStickyEvent(LCIMIMKickedMessageEvent.class);
        removeStickyEvent(SickCardMessageEvent.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

        Intent intent = new Intent();
        intent.setPackage(getApplicationContext().getPackageName());
        intent.setAction("com.kaurihealth.kaurihealth.main_v.MainActivity");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Bundle bundle = new Bundle();
        bundle.putInt(Global.Environment.BUNDLE, 2);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected static <T> void removeStickyEvent(Class<T> eventType) {
        T stickyEvent = EventBus.getDefault().getStickyEvent(eventType);
        if (stickyEvent != null) {
            EventBus.getDefault().removeStickyEvent((T) stickyEvent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initByIntent(intent);
    }

    private void initByIntent(Intent intent) {
        if (null == LCChatKit.getInstance().getClient()) {
            Toast.makeText(this, "聊天模块出现网络问题未初始化,请重新登陆本应用", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Bundle extras = intent.getExtras();
        if (null != extras) {
            if (extras.containsKey(LCIMConstants.PEER_ID)) {
                getConversation(extras.getString(LCIMConstants.PEER_ID));
            } else if (extras.containsKey(LCIMConstants.PEER_ID_GROUP)) {//创建群聊
                Conversation_Type = 1;
                mDisplayBean = extras.getParcelable(Global.Bundle.SEARCH_PATIENT);
                if (mDisplayBean != null) isSendCard = true;
                kauriHealthId = extras.getStringArrayList(LCIMConstants.PEER_ID_GROUP);
                if (kauriHealthId != null) isCreated = true;
                getConversation(kauriHealthId);
            } else if (extras.containsKey(LCIMConstants.CONVERSATION_ID)) {
                String conversationId = extras.getString(LCIMConstants.CONVERSATION_ID);
                AVIMClient client = LCChatKit.getInstance().getClient();
                updateConversation(client != null ? client.getConversation(conversationId) : null);
            } else if (extras.containsKey(LCIMConstants.CONVERSATION_ID_GROUP)) {
                String conversationId = extras.getString(LCIMConstants.CONVERSATION_ID_GROUP);
                Conversation_Type = 1;
                AVIMClient client = LCChatKit.getInstance().getClient();
                updateConversation(client != null ? client.getConversation(conversationId) : null);
            } else {
                showToast("memberId or conversationId is needed");
                finish();
            }
        }
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

    /**
     * 不包含本人的id集合
     *
     * @param kauriHealthId
     */
    protected void getConversation(List<String> kauriHealthId) {
        AVIMClient avimClient = LCChatKit.getInstance().getClient();
        if (avimClient != null) {
            fetchProfiles(kauriHealthId, avimClient);
        }
    }

    /**
     * 对话
     */
    private void getLCChatKitUser(String memberId, AVIMClient avimClient) {
        fetchProfiles(Arrays.asList(memberId), avimClient);
    }

    private void fetchProfiles(final List<String> memberIdList, final AVIMClient avimClient) {
        LCChatProfileProvider profileProvider = LCChatKit.getInstance().getProfileProvider();
        if (null != profileProvider) {
            profileProvider.fetchProfiles(memberIdList, new LCChatProfilesCallBack() {
                @Override
                public void done(List<ContactUserDisplayBean> userList, Exception e) {
                    if (null != userList && userList.size() > 0) {
                        createMembers(userList, memberIdList, avimClient);
                    }
                }
            });
        }
    }

    private void createMembers(List<ContactUserDisplayBean> userList, List<String> memberIdList, AVIMClient avimClient) {
        LCChatKitUser[] members = new LCChatKitUser[memberIdList.size() + 1];
        String[] names = new String[memberIdList.size()];
        for (int i = 0; i < memberIdList.size(); i++) {
            ContactUserDisplayBean contactUserDisplayBean = userList.get(i);
            members[i] = new LCChatKitUser(contactUserDisplayBean.getKauriHealthId(), contactUserDisplayBean.getFullName(), contactUserDisplayBean.getAvatar());
            names[i] = contactUserDisplayBean.getFullName();
        }

        UserBean userBean = LocalData.getLocalData().getTokenBean().getUser();
        members[members.length - 1] = new LCChatKitUser(userBean.getKauriHealthId(), userBean.getFullName(), userBean.getAvatar());
        if (names.length != 1) names[0] = userBean.getFullName();

        Map<String, Object> attr = new HashMap<>();
        attr.put(Global.LeanCloud.ATTR_MEMBERS, members);
        attr.put(Global.LeanCloud.ATTR_TYPE, Conversation_Type);

        createConversation(attr, avimClient, memberIdList, names);
    }

    /**
     * 创建群聊对话
     */
    private void createConversation(Map<String, Object> attr, AVIMClient avimClient, List<String> memberId, String[] names) {
        String name = "";
        if (names.length > 1) {
            name = names[0] + "、" + names[1] + "...";
        } else if (names.length == 1) {
            name = LocalData.getLocalData().getTokenBean().getUser().getFullName() + "、" + names[0];
        }
        avimClient.createConversation(memberId, name, attr, false, false, new AVIMConversationCreatedCallback() {
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
                        itemAdapter.showUserName(Conversation_Type == 1);
                    }
                });
            } else {
                itemAdapter.showUserName(Conversation_Type == 1);
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

                    if (!isSendCard && isCreated) {
                        if (EventBus.getDefault().hasSubscriberForEvent(LCIMIMJoinedMessageEvent.class)){
                            joinedMessage(kauriHealthId);
                            removeStickyEvent(LCIMIMJoinedMessageEvent.class);
                        }
                        isCreated = false;
                    }
                    if (isSendCard) {
                        initMainCard(mDisplayBean);
                        isSendCard = false;
                    }
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
        Toast.makeText(GroupConversationActivity.this, content, Toast.LENGTH_SHORT).show();
    }

    private boolean filterException(Exception e) {
        if (null != e) {
            LCIMLogUtils.logException(e);
            showToast(e.getMessage());
        }
        return (null == e);
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
            LCIMConversationItemCache.getInstance().clearUnread(mConversation.getConversationId());
        }
    }

    /**
     * 加人通知
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMIMJoinedMessageEvent messageEvent) {
        if (null != mConversation && null != messageEvent &&
                mConversation.getConversationId().equals(messageEvent.getConversationId())) {

            List<String> members = messageEvent.getMembers();
            joinedMessage(members);
        }
    }

    private void joinedMessage(List<String> members) {
        final JoinTypeMessage joinTypeMessage = new JoinTypeMessage();
        LCIMProfileCache.getInstance().getCachedUsers(members, new AVCallback<List<ContactUserDisplayBean>>() {
            @Override
            protected void internalDone0(List<ContactUserDisplayBean> beanList, AVException e) {
                if (beanList != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (ContactUserDisplayBean bean : beanList) {
                        stringBuilder.append(bean.getFullName()).append("、");
                    }
                    String mString = stringBuilder.toString();
                    String hiht = mString.substring(0, mString.length() - 1);
                    joinTypeMessage.setText(hiht + "加入本聊天群");
                    itemAdapter.addMessage(joinTypeMessage);
                    itemAdapter.notifyDataSetChanged();
                    scrollToBottom();
                }
            }
        });
    }

    /**
     * 移除
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMIMKickedMessageEvent messageEvent) {
        if (null != mConversation && null != messageEvent &&
                mConversation.getConversationId().equals(messageEvent.getConversationId())) {
            final JoinTypeMessage joinTypeMessage = new JoinTypeMessage();
            String creater = messageEvent.getInvitedBy();
            List<String> members = messageEvent.getMembers();
            LCIMProfileCache.getInstance().getCachedUsers(members, new AVCallback<List<ContactUserDisplayBean>>() {
                @Override
                protected void internalDone0(List<ContactUserDisplayBean> beanList, AVException e) {
                    if (beanList != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (ContactUserDisplayBean bean : beanList) {
                            stringBuilder.append(bean.getFullName()).append("、");
                        }
                        String mString = stringBuilder.toString();
                        String hiht = mString.substring(0, mString.length() - 1);
                        joinTypeMessage.setText(hiht + "移除本聊天群");
                        itemAdapter.addMessage(joinTypeMessage);
                        itemAdapter.notifyDataSetChanged();
                        scrollToBottom();
                    }
                }
            });
        }
    }

    /**
     * 滚动 recyclerView 到底部
     */
    private void scrollToBottom() {
        layoutManager.scrollToPositionWithOffset(itemAdapter.getItemCount() - 1, 0);
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
                case LCIMInputBottomBarEvent.INPUTBOTTOMBAR_MEDICAL_ACTION://6
                    referralByConversation(LCIMInputBottomBarEvent.INPUTBOTTOMBAR_MEDICAL_ACTION);
                    break;
                default:
                    break;
            }
        }
    }

    private void referralByConversation(final int action) {
        if (mConversation == null) return;
        DoctorDisplayBean myBean = LocalData.getLocalData().getMyself();
        if (myBean != null) {
            EventBus.getDefault().postSticky(
                    new ReferralPatientEvent(myBean.getDoctorId(), LCIMInputBottomBarEvent.INPUTBOTTOMBAR_MEDICAL_ACTION));
            Intent intent = new Intent();
            intent.setPackage(getApplicationContext().getPackageName());
            intent.setAction(LCIMConstants.CONVERSATION_ITEM_DETAIL_ACTION_REFERRAL_DOCTOR);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivityForResult(intent, Global.RequestCode.SICKNESS);
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
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
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
                case Global.RequestCode.Group_CHAT_DOCTOR:
                    finish();
                    break;
                case Global.RequestCode.SICKNESS:
                    if (data != null) {
                        sendSickCard(data);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 病历名片
     */
    private void sendSickCard(Intent data) {
        ArrayList<MainPagePatientDisplayBean> arrayListExtra = data.getParcelableArrayListExtra(Global.Bundle.RESULT_OK_SICKNESS);
        if (arrayListExtra != null && !arrayListExtra.isEmpty()) {
            for (MainPagePatientDisplayBean bean : arrayListExtra) {
                initMainCard(bean);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(SickCardMessageEvent messageEvent) {
        if (messageEvent != null) {
            MainPagePatientDisplayBean bean = messageEvent.getDisplayBean();
            initMainCard(bean);
        }
    }


    private void initMainCard(MainPagePatientDisplayBean bean) {
        List<OnlineMainPagePatientDisplayBean> list = new ArrayList<>();
        if (bean != null){
            OnlineMainPagePatientDisplayBean onlineBean = new OnlineMainPagePatientDisplayBean();
            onlineBean.setPatientRecordId(bean.getPatientRecordId());
            onlineBean.setPatientId(bean.getPatientId());
            onlineBean.setDoctorFullName(bean.getDoctorFullName());
            onlineBean.setPatientFullName(bean.getPatientFullName());
            onlineBean.setRecordType(bean.getRecordType());
            onlineBean.setGender(bean.getGender());
            onlineBean.setDateOfBirth(DateUtils.getDateText(bean.getDateOfBirth()));
            onlineBean.setLatestRecordDate(DateUtils.getDateText(bean.getLatestRecordDate()));
            onlineBean.setSicknesses(bean.getSicknesses());

            list.add(onlineBean);
        }

        AVIMTextMessage message = new AVIMTextMessage();
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("username", nameTitle.getText().toString().trim());
        attributes.put("messageType", 1);
        attributes.put("messageContent", list);
        message.setAttrs(attributes);
        message.setText("[名片]");
        sendMessage(message);
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
                }
            }
        });
    }

    /**
     * 详情页面
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mConversation != null) {
            Intent intent = new Intent();
            intent.setPackage(getApplicationContext().getPackageName());
            intent.setAction(LCIMConstants.CONVERSATION_ITEM_DETAIL_ACTION_GROUP);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(LCIMConstants.CONVERSATION_ID_DETAIL_GROUP, mConversation.getConversationId());
            startActivityForResult(intent, Global.RequestCode.Group_CHAT_DOCTOR);
        }
    }

}
