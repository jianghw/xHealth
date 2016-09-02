package com.kaurihealth.kaurihealth.home.bean;

import android.support.v4.app.Fragment;

import com.kaurihealth.kaurihealth.home.util.ISearch;
import com.kaurihealth.kaurihealth.mine.bean.FragmentBean;

/**
 * Created by 张磊 on 2016/7/26.
 * 介绍：
 */
public class FragmentTitleSearchBean extends FragmentBean{
    public ISearch search;

    public FragmentTitleSearchBean(ISearch search) {
        this.search = search;
    }

    public FragmentTitleSearchBean(Fragment fragment, String title, ISearch search) {
        super(fragment, title);
        this.search = search;
    }

}
