package com.kaurihealth.kaurihealth.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.SearchDoctorDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/3/21.
 * 备注：
 */
public class DoctorAdapter extends CommonAdapter<SearchDoctorDisplayBean> {

    public DoctorAdapter(Context context, List<SearchDoctorDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.blankdotor_iteam, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    class ViewHolder {

        @Bind(R.id.tv_blanname)
        TextView tv_blanname;

        @Bind(R.id.tv_gender)
        TextView tv_gender;

        @Bind(R.id.tv_departmentName)
        TextView tv_departmentName;

        @Bind(R.id.tv_hospitalTitle)
        TextView tv_hospitalTitle;

        @Bind(R.id.tv_hospitalName)
        TextView tv_hospitalName;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(SearchDoctorDisplayBean searchBean) {
            tv_blanname.setText(searchBean.fullName);
            if (searchBean.gender==0){
                tv_gender.setText("男");
            }else {

                tv_gender.setText("女");
            }

            tv_departmentName.setText(searchBean.departmentName);
            tv_hospitalTitle.setText(searchBean.hospitalTitle);
            tv_hospitalName.setText(searchBean.hospitalName);
        }
    }

}
