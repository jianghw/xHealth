package com.kaurihealth.kaurihealth.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.bugtags.library.Bugtags;
import com.example.chatlibrary.bean.ChatBean;
import com.example.chatlibrary.chat.ChatInjection;
import com.example.chatlibrary.chat.ChatUtils;
import com.example.chatlibrary.chat.SingleChat;
import com.example.commonlibrary.widget.util.GetDataInterface;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.chat.activity.SingleChatActivity;
import com.kaurihealth.kaurihealth.chat.adapter.MessageAdapter;
import com.kaurihealth.kaurihealth.util.Getter;
import com.kaurihealth.kaurihealth.util.Interface.IGetter;
import com.youyou.zllibrary.util.CommonFragment;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class MessageFragment extends CommonFragment {

    @Bind(R.id.lay_title_medicalhistory)
    RelativeLayout mLayTitleMedicalhistory;
    @Bind(R.id.lv_content)
    ListView mLvContent;
    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;

    private List<AVIMConversation> list = new LinkedList<>();
    private MessageAdapter adapter;
    private ChatUtils chatUtils = new ChatUtils();
    private String kauriHealthId;
    private IGetter getter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, view);

        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private SuccessInterfaceM<Boolean> listener;

    public void setMessageComeListener(SuccessInterfaceM<Boolean> listener) {
        this.listener = listener;
    }


    @Override
    public void init() {
        super.init();

        getter = Getter.getInstance(getActivity().getApplicationContext());
        kauriHealthId = getter.getKauriHealthId();
        adapter = new MessageAdapter(getContext(), list, this.kauriHealthId);
        adapter.setMessageComeListener(listener);
        mLvContent.setAdapter(adapter);

        mSwipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);
        mSwipeRefresh.setColorSchemeResources(
                R.color.holo_blue_light_new, R.color.holo_blue_light_new,
                R.color.holo_blue_light_new, R.color.holo_blue_light_new);

        mSwipeRefresh.setProgressBackgroundColor(R.color.linelogin);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chatUtils.getAllConversation(new AVIMConversationQueryCallback() {
                    @Override
                    public void done(final List<AVIMConversation> list, AVIMException e) {
                        if (e == null) {
                            ClearConversationUtil clearConversationUtil = new ClearConversationUtil();
                            clearConversationUtil.clearConversationIfNoSendMes(list, new SuccessInterface() {
                                @Override
                                public void success() {
                                    MessageFragment.this.list.clear();
                                    MessageFragment.this.list.addAll(list);
                                    adapter.notifyDataSetChanged();
                                    showToast("获取对话成功");
                                    clearDot();
                                }
                            });
                        } else {
                            logUtil.e(e.getMessage());
                            showToast("获取对话数据失败");
                            Bugtags.sendException(e);
                        }
                        refreshComplete();
                    }
                });
            }
        });
        /**```````````````````````分割线````````````````````````**/
        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object attribute = list.get(position).getAttribute(SingleChat.AttributeKey);
                String attributeStr = attribute.toString();
                logUtil.i(attributeStr);
                if (attribute != null) {
                    List<ChatBean> chatBeen = JSON.parseArray(attributeStr, ChatBean.class);
                    for (int i = 0; i < chatBeen.size(); i++) {
                        ChatBean chatBean = chatBeen.get(i);
                        if (!(chatBean.getClientId().equals(MessageFragment.this.kauriHealthId))) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", chatBean);
                            skipTo(SingleChatActivity.class, bundle);
                        }
                    }
                }

            }
        });
    }

    private void refreshComplete() {
        if (mSwipeRefresh != null) {
            mSwipeRefresh.setRefreshing(false);
        }
    }

    public GetDataInterface getDataInterface() {
        chatUtils.setChatInterface(new SingleChat(ChatInjection.avimClient, ChatInjection.control));

        return new GetDataInterface() {
            @Override
            public void getData() {
                chatUtils.getAllConversation(new AVIMConversationQueryCallback() {
                    @Override
                    public void done(final List<AVIMConversation> list, final AVIMException e) {
                        logUtil.i("getDataInterface");
                        if (e == null) {
                            ClearConversationUtil clearConversationUtil = new ClearConversationUtil();
                            clearConversationUtil.clearConversationIfNoSendMes(list, new SuccessInterface() {
                                @Override
                                public void success() {
                                    MessageFragment.this.list.clear();
                                    MessageFragment.this.list.addAll(list);
                                    clearDot();
                                    if (MessageFragment.this.isAdded()) {
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            });
                        } else {
                            Bugtags.sendException(e);
                            logUtil.e(e.getMessage());
                        }
                        refreshComplete();
                    }
                });
            }
        };
    }

    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    @Override
    public void onResume() {
        super.onResume();
        logUtil.i("onResume");
        //重新查询，达到排序效果
        chatUtils.getAllConversation(new AVIMConversationQueryCallback() {
            @Override
            public void done(final List<AVIMConversation> list, final AVIMException e) {
                if (e == null) {
                    ClearConversationUtil clearConversationUtil = new ClearConversationUtil();
                    clearConversationUtil.clearConversationIfNoSendMes(list, new SuccessInterface() {
                        @Override
                        public void success() {
                            MessageFragment.this.list.clear();
                            MessageFragment.this.list.addAll(list);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    logUtil.e(e.getMessage());
                    Bugtags.sendException(e);
                }
                refreshComplete();

            }
        });
        adapter.receiveMessage();
        conversationIndex = list.size();
        AVIMConversationCallback callback = new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                conversationIndex--;
                if (conversationIndex == 0) {
                    clearDot();
                    adapter.notifyDataSetChanged();
                }
            }
        };
        for (AVIMConversation iteam : list) {
            iteam.fetchInfoInBackground(callback);
        }
        adapter.notifyDataSetChanged();
    }

    int conversationIndex;

    private int dotIndex = 0;
    private boolean MainDotShow = false;

    /**
     * 判断是否清除主界面的小红点,逻辑：只要有一个消息未读，就显示小红点
     */
    public void clearDot() {
        if (list == null || list.size() == 0) {
            return;
        }
        dotIndex = list.size();
        MainDotShow = false;
        SuccessInterfaceM<Boolean> successInterfaceM = new SuccessInterfaceM<Boolean>() {
            @Override
            public void success(Boolean hasRead) {
                dotIndex--;
                if (!hasRead) {
                    MainDotShow = true;
                }
                if (dotIndex == 0) {
                    listener.success(MainDotShow);
                }
            }
        };
        for (int i = 0; i < list.size(); i++) {
            AVIMConversation curConversation = list.get(i);
            ChatInjection.hasReadConversation(curConversation, successInterfaceM, kauriHealthId);
        }
    }


    class ClearConversationUtil {
        /**
         * 清除没有发送过消息的对话
         */
        Integer temp;

        public void clearConversationIfNoSendMes(List<AVIMConversation> avimConversationList, final SuccessInterface clearOverListener) {
            clearOverListener.success();
        }
    }
}
