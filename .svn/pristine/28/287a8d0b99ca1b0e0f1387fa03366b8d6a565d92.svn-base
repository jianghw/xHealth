package com.kaurihealth.utilslib.widget;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by jianghw on 2016/11/24.
 * <p/>
 * Describe:
 */
public class HeaderFooterListener implements
        AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, AdapterView.OnItemSelectedListener {

    private AdapterView.OnItemClickListener itemClickListener;
    private AdapterView.OnItemLongClickListener itemLongClickListener;
    private AdapterView.OnItemSelectedListener itemSelectedListener;
    private HeaderFooterViewListAdapter mAdapter;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(parent, view, position - (mAdapter == null ? 0 : mAdapter.getHeaderViewsCount()), id);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return itemLongClickListener != null &&
                itemLongClickListener.onItemLongClick(parent, view, position - (mAdapter == null ? 0 : mAdapter.getHeaderViewsCount()), id);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (itemSelectedListener != null)
            itemSelectedListener.onItemSelected(parent, view, position - (mAdapter == null ? 0 : mAdapter.getHeaderViewsCount()), id);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        if (itemSelectedListener != null) itemSelectedListener.onNothingSelected(parent);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener, HeaderFooterViewListAdapter adapter) {
        itemClickListener = listener;
        mAdapter = adapter;
    }

    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener, HeaderFooterViewListAdapter adapter) {
        itemLongClickListener = listener;
        mAdapter = adapter;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener, HeaderFooterViewListAdapter adapter) {
        itemSelectedListener = listener;
        mAdapter = adapter;
    }
}
