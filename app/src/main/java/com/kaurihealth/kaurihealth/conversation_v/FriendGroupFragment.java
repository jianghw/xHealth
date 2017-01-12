package com.kaurihealth.kaurihealth.conversation_v;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import butterknife.Bind;

/**
 * Created by jianghw on 2016/11/2.
 * <p/>
 * Describe: 圈子
 */

public class FriendGroupFragment extends ChildBaseFragment {

    @Bind(R.id.tv_no_date)
    TextView mTextView;
    @Bind(R.id.rv_conversation)
    ListView mRvConversation;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    public static FriendGroupFragment newInstance() {
        return new FriendGroupFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_conversation_items;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
    }

    @Override
    protected void initDelayedData() {
    }

    @Override
    protected void childLazyLoadingData() {
    }

    public void loadingIndicator(boolean flag) {
        if (mSwipeRefresh == null) return;
        mSwipeRefresh.post(() -> mSwipeRefresh.setRefreshing(flag));
    }

}
