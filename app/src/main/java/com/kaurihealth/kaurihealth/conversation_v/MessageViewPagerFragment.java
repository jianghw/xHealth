package com.kaurihealth.kaurihealth.conversation_v;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseFragment;
import com.kaurihealth.kaurihealth.base_v.ChildBaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 医患关系 母页
 * 描述:消息Fragment
 */
public class MessageViewPagerFragment extends BaseFragment {

    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.vp_content)
    ViewPager mVpContent;
    @Bind(R.id.tv_add_group)
    TextView mTvAddGroup;

    List<Fragment> mFragmentList = new ArrayList<>();
    /**
     * 当前指标
     */
    private int mPosition = 0;

    public static MessageViewPagerFragment newInstance() {
        return new MessageViewPagerFragment();
    }

    @Override
    protected int getFragmentLayoutID() {
        return R.layout.fragment_vp_message;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        createCurrentFragment();
    }

    private void createCurrentFragment() {
        if (mFragmentList.size() > 0) mFragmentList.clear();
        ConversationItemFragment conversationItemFragment = ConversationItemFragment.newInstance();
        ConversationGroupItemFragment conversationGroupItemFragment = ConversationGroupItemFragment.newInstance();
        FriendGroupFragment friendGroupFragment = FriendGroupFragment.newInstance();

        mFragmentList.add(conversationItemFragment);
        mFragmentList.add(conversationGroupItemFragment);
        mFragmentList.add(friendGroupFragment);
    }

    @Override
    protected void initDelayedData() {
        String[] arrays = getResources().getStringArray(R.array.message_details_tab);
        initViewPager(arrays);

        mTvAddGroup.setVisibility(mPosition == 1 ? View.VISIBLE : View.GONE);
    }

    private void initViewPager(String[] arrays) {
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getChildFragmentManager(), mFragmentList, arrays);
        mVpContent.setAdapter(mainFragmentAdapter);
        mVpContent.setOffscreenPageLimit(mFragmentList.size() - 1);//设置预加载
        mVpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                mTvAddGroup.setVisibility(mPosition == 1 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mVpContent);
        //给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(mainFragmentAdapter);
    }

    @Override
    protected void lazyLoadingData() {

    }

    /**
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getActivity() != null) {
            List<Fragment> childFragmentList = getChildFragmentManager().getFragments();
            if (isVisibleToUser) {
                // 将所有正等待显示的子Fragment设置为显示状态，并取消等待显示标记
                if (childFragmentList != null && childFragmentList.size() > 0) {
                    for (Fragment childFragment : childFragmentList) {
                        if (childFragment instanceof ChildBaseFragment) {
                            ChildBaseFragment childBaseFragment = (ChildBaseFragment) childFragment;
                            if (childBaseFragment.isWaitingShowToUser()) {
                                childBaseFragment.setWaitingShowToUser(false);
                                childFragment.setUserVisibleHint(true);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (!mFragmentList.isEmpty()) mFragmentList.clear();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_add_group)
    public void onTvClick() {
        skipTo(AddGroupChatActivity.class);
    }

    public ViewPager getParentViewPager(){
        return mVpContent;
    }
}
