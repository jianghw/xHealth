package com.kaurihealth.kaurihealth.main_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.chatlibrary.ChatFactory;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.utilslib.widget.UiTableBottom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;

/**
 * Created by jianghw on 2016/8/11.
 * <p>
 * 描述：
 */
public class MainActivity extends BaseActivity {

    List<Fragment> mFragmentList = new ArrayList<>();

    @Bind(R.id.vp_content_main)
    ViewPager mVpContentMain;
    @Bind(R.id.ui_bottom)
    UiTableBottom mUiBottom;

    private int curBottomItem = 0;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenterAndData(Bundle savedInstanceState) {
        initChatMessageData();
        createCurrentFragment();
    }

    /**
     * 是否有未读消息
     */
    private void initChatMessageData() {
        ChatFactory.init(getApplicationContext(), LocalData.getLocalData().getEnvironment());
        ChatFactory.instance().createAVIMClient(getKauriHealthId(), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {

            }
        });
    }

    private String getKauriHealthId() {
        UserBean user = LocalData.getLocalData().getTokenBean().getUser();
        if (user == null) throw new IllegalStateException("userBean is null ,it must be not null");
        return user.getKauriHealthId();
    }

    private void createCurrentFragment() {
        if (mFragmentList.size() > 0) mFragmentList.clear();
        HomeFragment homeFragment = HomeFragment.newInstance();
        MessageFragment messageFragment = MessageFragment.newInstance();
        RelationFragment relationFragment = RelationFragment.newInstance();

        ClinicalFragment clinicalFragment = ClinicalFragment.newInstance();
        MineFragment mineFragment = MineFragment.newInstance();

        mFragmentList.add(homeFragment);
        mFragmentList.add(messageFragment);
        mFragmentList.add(relationFragment);
        mFragmentList.add(clinicalFragment);
        mFragmentList.add(mineFragment);

    }

    @Override
    protected void initDelayedView() {
        initViewPager();
        initBottomTable();
    }

    private void initBottomTable() {
        HashMap<Integer, Integer[]> hashMap = new HashMap<>();
        hashMap.put(0, new Integer[]{R.mipmap.ic_main_home_sel, R.mipmap.ic_main_home_nor, R.string.main_table_home});
        hashMap.put(1, new Integer[]{R.mipmap.ice_main_chat_sel, R.mipmap.ic_main_chat_nor, R.string.main_table_message});
        hashMap.put(2, new Integer[]{R.mipmap.ic_main_patient_sel, R.mipmap.ic_main_patient_nor, R.string.main_table_patient});
        hashMap.put(3, new Integer[]{R.mipmap.ic_main_clinical_sel, R.mipmap.ic_main_clinical_nor, R.string.main_table_clinical});
        hashMap.put(4, new Integer[]{R.mipmap.ic_main_mine_sel, R.mipmap.ic_main_mine_nor, R.string.main_table_mine});

        mUiBottom.setUiViewPager(new UiTableBottom.OnUITabChangListener() {
            @Override
            public void onTabChang(int index) {
                mVpContentMain.setCurrentItem(index);
            }
        }, curBottomItem, hashMap);

    }


    private void initViewPager() {
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), mFragmentList);
        mVpContentMain.setAdapter(mainFragmentAdapter);
        mVpContentMain.setOffscreenPageLimit(mFragmentList.size() - 1);//设置预加载
        mVpContentMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                curBottomItem = position;
                mUiBottom.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}
