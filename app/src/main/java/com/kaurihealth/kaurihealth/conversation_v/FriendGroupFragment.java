package com.kaurihealth.kaurihealth.conversation_v;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;

import butterknife.Bind;

/**
 * Created by jianghw on 2016/11/2.
 * <p/>
 * Describe: 圈子
 */

public class FriendGroupFragment extends ChildBaseFragment {


    @Bind(R.id.lay_title)
    RelativeLayout mLayTitle;

    public static FriendGroupFragment newInstance() {
        return new FriendGroupFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_empty;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        mLayTitle.setVisibility(View.GONE);
    }

    @Override
    protected void initDelayedData() {
    }

    @Override
    protected void childLazyLoadingData() {
    }

}
