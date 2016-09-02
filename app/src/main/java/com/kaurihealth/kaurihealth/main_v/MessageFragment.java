package com.kaurihealth.kaurihealth.main_v;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.avos.avoscloud.im.v2.AVIMConversation;
import com.example.chatlibrary.ChatFactory;
import com.kaurihealth.kaurihealth.MyApplication;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.chat.adapter.MessageAdapter;
import com.kaurihealth.mvplib.main_p.IMessageView;
import com.kaurihealth.mvplib.main_p.MessagePresenter;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;


public class MessageFragment extends BaseFragment implements IMessageView {

    @Bind(R.id.lv_content)
    ListView mLvContent;

    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    @Inject
    MessagePresenter<IMessageView> mPresenter;
    /**
     * 所有聊天者信息
     */
    private LinkedList<AVIMConversation> mAVIMConversations;
    private MessageAdapter adapter;

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initPresenterAndData() {
        MyApplication.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);

        mAVIMConversations = new LinkedList<>();

        adapter = new MessageAdapter(getContext(), mAVIMConversations, ChatFactory.instance().getClientId());
        //        adapter.setMessageComeListener(listener);
        mLvContent.setAdapter(adapter);
    }

    @Override
    protected void initDelayedView() {

        mSwipeRefresh.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        mSwipeRefresh.setScrollUpChild(mLvContent);
        mSwipeRefresh.setDistanceToTriggerSync(300);
        mSwipeRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.loadingRemoteData(true);
                    }
                });
    }

    @Override
    protected void lazyLoadingData() {
        mPresenter.onSubscribe();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAVIMConversations != null) mAVIMConversations.clear();
        mPresenter.unSubscribe();
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public void AllConversationsError(String message) {
        showToast(message);
    }

    @Override
    public void AllConversationsSuccess(List<AVIMConversation> list) {
        if (mAVIMConversations == null)
            throw new IllegalStateException("mAVIMConversations must be not null");
        if (mAVIMConversations.size() > 0) mAVIMConversations.clear();
        mAVIMConversations.addAll(list);
        adapter.notifyDataSetChanged();
    }
}
