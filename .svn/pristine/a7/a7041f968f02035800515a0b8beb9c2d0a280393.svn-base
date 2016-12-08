package com.kaurihealth.kaurihealth.main_v;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.chatlib.cache.LCIMConversationItem;
import com.kaurihealth.chatlib.cache.LCIMConversationItemCache;
import com.kaurihealth.chatlib.event.LCIMConnectionChangeEvent;
import com.kaurihealth.chatlib.event.LCIMOfflineMessageCountChangeEvent;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.SoftwareInfo;
import com.kaurihealth.datalib.response_bean.UserBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.MainFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.kaurihealth.main_v.NewestVersion.GetNewestVersion;
import com.kaurihealth.kaurihealth.main_v.NewestVersion.NetLastVersionAbstract;
import com.kaurihealth.kaurihealth.main_v.NewestVersion.Url;
import com.kaurihealth.kaurihealth.tinker.SampleApplicationLike;
import com.kaurihealth.mvplib.base_p.Listener;
import com.kaurihealth.mvplib.main_p.IMainView;
import com.kaurihealth.mvplib.main_p.MainPresenter;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.widget.UiTableBottom;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by jianghw on 2016/8/11.
 * <p/>
 * 描述： 母页
 */
public class MainActivity extends BaseActivity implements IMainView {
    @Inject
    MainPresenter<IMainView> mPresenter;

    @Bind(R.id.vp_content_main)
    ViewPager mVpContentMain;

    @Bind(R.id.ui_bottom)
    UiTableBottom mUiBottom;

    List<Fragment> mFragmentList = new ArrayList<>();
    private int curBottomItem = 0;
    private long exitTime;
    Handler handler = new Handler();

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        SampleApplicationLike.getApp().getComponent().inject(this);
        mPresenter.setPresenter(this);
        createCurrentFragment();
    }

    /**
     * 网路状态      eventBusMain
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventBusMain(LCIMConnectionChangeEvent event) {
        if (event.isConnect) initChatMessageData();
        Toast.makeText(MainActivity.this, event.isConnect ? "聊天为在线状态" : "聊天为离线状态,请注意网络", Toast.LENGTH_SHORT).show();
    }

    /**
     * 离线消息数量发生变化是响应此事件
     * 避免登陆后先进入此页面，然后才收到离线消息数量的通知导致的页面不刷新的问题
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void eventBusMain(LCIMOfflineMessageCountChangeEvent event) {
        int count = 0;
        List<LCIMConversationItem> converList = LCIMConversationItemCache.getInstance().getConversationList();
        for (LCIMConversationItem conversationItem : converList) {
            count = count + conversationItem.unreadCount;
        }

        if (mUiBottom != null) mUiBottom.setTipOfNumber(1, count);
    }

    /**
     * 初始化聊天
     */
    private void initChatMessageData() {
        LCChatKit.getInstance().open(getKauriHealthId(), new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                mPresenter.onSubscribe();
                if (e != null) showToast("重新连接聊天服务失败,检查网络" + e.getMessage());
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
    protected void initDelayedData() {
        initViewPager();
        initBottomTable();
        NetLastVersionAbstract versionCheck = new GetNewestVersion(handler, this, Url.LoadAndroidVersionWithDoctor);
        versionCheck.startWithDownloadManager();

        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    mPresenter.onSubscribe();
                }
            }
        });

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        removeStickyEvent(LCIMConnectionChangeEvent.class);
        removeStickyEvent(LCIMOfflineMessageCountChangeEvent.class);
        mPresenter.unSubscribe();
        EventBus.getDefault().unregister(this);
        if (!mFragmentList.isEmpty()) mFragmentList.clear();
    }

    private void initBottomTable() {
        HashMap<Integer, Integer[]> hashMap = new HashMap<>();
        hashMap.put(0, new Integer[]{R.mipmap.ic_main_home_sel, R.mipmap.ic_main_home_nor, R.string.main_table_home});
        hashMap.put(1, new Integer[]{R.mipmap.ice_main_chat_sel, R.mipmap.ic_main_chat_nor, R.string.main_table_message});
        hashMap.put(2, new Integer[]{R.mipmap.ic_main_patient_sel, R.mipmap.ic_main_patient_nor, R.string.main_table_patient});
        hashMap.put(3, new Integer[]{R.mipmap.ic_main_clinical_sel, R.mipmap.ic_main_clinical_nor, R.string.main_table_clinical});
        hashMap.put(4, new Integer[]{R.mipmap.ic_main_mine_sel, R.mipmap.ic_main_mine_nor, R.string.main_table_mine});

        mUiBottom.setUiViewPager(index -> mVpContentMain.setCurrentItem(index), curBottomItem, hashMap);
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

    public void setCurrentPager(int position) {
        curBottomItem = position;
        mUiBottom.selectTab(position);
    }

    @Override
    public void switchPageUI(String className) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("请再点击一次,退出应用");
                exitTime = System.currentTimeMillis();
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式了
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    public void CheckVersion(Map<String, String> map, Listener<SoftwareInfo> listener) {
        mPresenter.checkVersion(map, listener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Global.RequestCode.SEARCH_PATIENT) {
                setCurrentPager(2);
            }
        }
    }

    @Override
    public String getPushNotificationDeviceIdentityToken() {
        return AVInstallation.getCurrentInstallation().getInstallationId();
    }
}
