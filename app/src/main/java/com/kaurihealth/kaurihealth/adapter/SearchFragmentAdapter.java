package com.kaurihealth.kaurihealth.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 搜索页面用的
 */
public class SearchFragmentAdapter extends FragmentPagerAdapter {
    private final String[] stringArray;
    private final List<Fragment> list;

    public SearchFragmentAdapter(FragmentManager fm, List<Fragment> list, String[] stringArray) {
        super(fm);
        this.list = list;
        this.stringArray = stringArray;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringArray[position];
    }

}
