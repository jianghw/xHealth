package com.youyou.zllibrary.util;

import android.content.Context;
import android.view.LayoutInflater;

import com.youyou.zllibrary.widget.AnimatedExpandableListView;

import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 创建日期:2016/1/22
 * 修订日期:
 */
public abstract class CommonAnimatedExpandableListAdapter<T> extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    protected final LayoutInflater inflater;
    protected List<T> data;

    public CommonAnimatedExpandableListAdapter(Context context, List<T> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
