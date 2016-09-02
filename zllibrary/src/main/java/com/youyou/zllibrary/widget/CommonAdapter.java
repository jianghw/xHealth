package com.youyou.zllibrary.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 创建时间: 2015/12/25
 * 创建人:   张磊
 * 描述:
 * 对Baseadapter的封装
 * 修订时间:
 * 使用说明:
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected List<T> list;
    protected Context context;
    protected LayoutInflater inflater;

    public CommonAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
