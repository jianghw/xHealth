package com.kaurihealth.kaurihealth.patientwithdoctor.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.DepartmentDisplayWrapBean;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/6/21.
 * 介绍：
 */
public class DepartmentLevel1Adapter extends CommonAdapter<DepartmentDisplayWrapBean> {

    public DepartmentLevel1Adapter(Context context, List<DepartmentDisplayWrapBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.departmentlevel_iteam, null);
            ButterKnife.bind(this, convertView);
            convertView.setTag(new ViewHolder(convertView));
        }
            viewHolder  = (ViewHolder) convertView.getTag();

        DepartmentDisplayWrapBean bean = list.get(position);
        viewHolder.tvContent.setText(bean.level1.departmentName);
        return convertView;
    }


    public static class ViewHolder {
       @Bind(R.id.tvContent)
       TextView tvContent;
        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
