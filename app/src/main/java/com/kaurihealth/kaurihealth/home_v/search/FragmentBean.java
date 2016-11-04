package com.kaurihealth.kaurihealth.home_v.search;

import android.support.v4.app.Fragment;

/**
 * Created by 张磊 on 2016/7/18.
 * 介绍：
 */
public class FragmentBean {
    public Fragment fragment;
    public String title;

    public FragmentBean() {
    }

    public FragmentBean(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }
}
