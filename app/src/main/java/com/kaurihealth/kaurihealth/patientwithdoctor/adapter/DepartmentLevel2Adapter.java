package com.kaurihealth.kaurihealth.patientwithdoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.widget.CommonAdapter;

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
            convertView = inflater.inflate(R.layout.departmentlevel_iteam, null);
            ButterKnife.bind(this, convertView);
        }
        DepartmentDisplayBean iteam = list.get(position);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        tvContent.setText(iteam.departmentName);
        return convertView;
    }
}
