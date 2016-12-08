package com.kaurihealth.kaurihealth.mine_v;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.SearchFragmentAdapter;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by mip on 2016/11/1.
 */

public class SetupprotocolActivityNew extends BaseActivity {
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    SearchFragmentAdapter adapter;

    List<Fragment> fragmentList = new ArrayList<>();//页面


    @Override
    protected int getActivityLayoutID() {
        return R.layout.indicator_layout;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {

    }

    @Override
    protected void initDelayedData() {
        initBackBtn(R.id.iv_back);
        Fragment exclusiveFragment = new ExclusiveFragment();//专属医生服务
        Fragment remoteFragment = new RemoteFragment();//远程医疗服务
        Fragment outpatientFragment = new OutpatientFragment();//门诊
        fragmentList.add(exclusiveFragment);
        fragmentList.add(remoteFragment);
        fragmentList.add(outpatientFragment);

        String stringArray[] = new String[]{"专属医生服务","远程医疗服务","门诊"};
        adapter = new SearchFragmentAdapter(getSupportFragmentManager(), fragmentList, stringArray);
        viewpager.setAdapter(adapter);
        viewpager.setOffscreenPageLimit(fragmentList.size() - 1);
        tabs.setupWithViewPager(viewpager);
    }
}
