package com.kaurihealth.kaurihealth.main_v;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.utilslib.widget.ScrollChildSwipeRefreshLayout;

import butterknife.Bind;

/**
 * Created by jianghw on 2016/11/2.
 * <p/>
 * Describe: 医生或者患者 历史消息
 */

public class ConversationItemActivity extends BaseActivity {

    @Bind(R.id.rv_conversation)
    RecyclerView mRvConversation;
    @Bind(R.id.swipe_refresh)
    ScrollChildSwipeRefreshLayout mSwipeRefresh;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_conversation_items;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
in
    }

    @Override
    protected void initDelayedData() {

    }

}
