package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.kaurihealth.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/6/21.
 * 介绍：
 */
public class DepartmentLevel2Adapter extends CommonAdapter<DepartmentDisplayBean> {

    public DepartmentLevel2Adapter(Context context, List<DepartmentDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.departmentlevel_item_two, null);
            ButterKnife.bind(this, convertView);
        }
        DepartmentDisplayBean item = list.get(position);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        tvContent.setText(item.getDepartmentName());
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }
}
