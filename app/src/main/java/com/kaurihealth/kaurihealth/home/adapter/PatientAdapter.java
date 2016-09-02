package com.kaurihealth.kaurihealth.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.datalib.request_bean.bean.PatientDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.netutil.ImageLoadUtil;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PatientAdapter extends CommonAdapter<PatientRequestDisplayBean> {


    public PatientAdapter(Context context, List<PatientRequestDisplayBean> list) {
        super(context, list);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.homeiteam, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.iv_photo_homeiteam)
        CircleImageView ivPhotoHomeiteam;
        @Bind(R.id.tv_name_homeiteam)
        TextView tvNameHomeiteam;
        @Bind(R.id.tv_time_homitam)
        TextView tvTimeHomitam;

        @Bind(R.id.tv_reason_homeiteam)
        TextView tv_reason_homeiteam;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(PatientRequestDisplayBean patientRequestDisplayBean) {
            PatientDisplayBean patientDisplayBean = patientRequestDisplayBean.patient;
            ImageLoadUtil.displayImg(patientDisplayBean.avatar, ivPhotoHomeiteam, context);
            tvNameHomeiteam.setText(patientDisplayBean.fullName);
            tvTimeHomitam.setText(DateUtils.GetDateText(patientRequestDisplayBean.requestDate ,"yyyy-MM-dd"));

            tv_reason_homeiteam.setText(patientRequestDisplayBean.requestReason);
        }
    }
}
