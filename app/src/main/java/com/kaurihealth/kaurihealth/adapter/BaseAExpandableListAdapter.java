package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.kaurihealth.utilslib.widget.AnimatedExpandableListView;

import java.util.List;

/**
 * 创建日期:2016/1/22
 * 修订:
 */
public abstract class BaseAExpandableListAdapter<T> extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    protected final LayoutInflater inflater;
    protected final Context mContext;
    protected List<T> data;

    public BaseAExpandableListAdapter(Context context, List<T> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
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
