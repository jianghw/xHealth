package com.kaurihealth.chatlib.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.kaurihealth.chatlib.R;
import com.kaurihealth.chatlib.adapter.LCIMCommonListAdapter;
import com.kaurihealth.chatlib.custom.LCIM_DividerItemDecoration;
import com.kaurihealth.chatlib.viewholder.LCIMConversationItemHolder;

/**
 * Created by wli on 16/2/29.
 * 会话列表页
 */
public class LCIMConversationListFragment extends Fragment {
    protected SwipeRefreshLayout refreshLayout;
    protected RecyclerView recyclerView;

    protected LCIMCommonListAdapter<AVIMConversation> itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lcim_conversation_list_fragment, container, false);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        refreshLayout.setEnabled(false);

        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_conversation_srl_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LCIM_DividerItemDecoration(getActivity()));

        itemAdapter = new LCIMCommonListAdapter<>(LCIMConversationItemHolder.class);
        recyclerView.setAdapter(itemAdapter);
//        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
    }

//    /**
//     * 收到对方消息时响应此事件
//     *
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void eventBusMain(LCIMIMTypeMessageEvent event) {
//        updateConversationList();
//    }
//
//    /**
//     * 删除会话列表中的某个 item
//     *
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void eventBusMain(LCIMConversationItemLongClickEvent event) {
//        if (null != event.conversation) {
//            String conversationId = event.conversation.getConversationId();
//            LCIMConversationItemCache.getInstance().deleteConversation(conversationId);
//            updateConversationList();
//        }
//    }
//
//    /**
//     * 离线消息数量发生变化是响应此事件
//     * 避免登陆后先进入此页面，然后才收到离线消息数量的通知导致的页面不刷新的问题
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void eventBusMain(LCIMOfflineMessageCountChangeEvent event) {
//        updateConversationList();
//    }

    /**
     * 刷新页面
     */
    private void updateConversationList() {
        //convIdList 为convID集合
//        List<String> convIdList = LCIMConversationItemCache.getInstance().getSortedConversationList();
//        LogUtils.jsonDate(convIdList);
//        List<AVIMConversation> conversationList = new ArrayList<>();
//        for (String convId : convIdList) {
//            AVIMClient client = LCChatKit.getInstance().getClient();
//            if (client != null)
//                conversationList.add(client.getConversation(convId));
//        }
//        itemAdapter.setDataList(conversationList);
//        itemAdapter.notifyDataSetChanged();
    }
}
