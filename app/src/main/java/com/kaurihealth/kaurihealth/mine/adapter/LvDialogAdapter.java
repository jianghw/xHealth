package com.kaurihealth.kaurihealth.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.List;

/**
 * Created by 张磊 on 2016/7/14.
 * 介绍：
 */
public class LvDialogAdapter extends CommonAdapter<String> {
    public LvDialogAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.lv_dialog_iteam, null);
        }
        ((TextView) convertView).setText(list.get(i));
        return convertView;
    }
}
