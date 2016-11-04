package com.kaurihealth.kaurihealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kaurihealth.kaurihealth.home_v.search.FragmentBean;

import java.util.List;

/**
 * Created by 张磊 on 2016/7/18.
 * 介绍：
 */
public class TabLayoutPageAdapter extends FragmentPagerAdapter {
    List<? extends FragmentBean> list;

    public TabLayoutPageAdapter(FragmentManager fm, List<? extends FragmentBean> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position).fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).title;
    }

}
