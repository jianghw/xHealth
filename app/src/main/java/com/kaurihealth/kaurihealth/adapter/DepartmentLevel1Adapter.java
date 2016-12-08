package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.kaurihealth.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/6/21.
 * 介绍：
 */
public class DepartmentLevel1Adapter extends CommonAdapter<DepartmentDisplayBean> {

    public DepartmentLevel1Adapter(Context context, List<DepartmentDisplayBean> departmentDisplayBeanList) {
        super(context, departmentDisplayBeanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.departmentlevel_item, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        viewHolder = (ViewHolder) convertView.getTag();

        DepartmentDisplayBean bean = list.get(position);
        viewHolder.tvContent.setText(bean.getDepartmentName());
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    public static class ViewHolder {
        @Bind(R.id.tvContent)
        TextView tvContent;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
