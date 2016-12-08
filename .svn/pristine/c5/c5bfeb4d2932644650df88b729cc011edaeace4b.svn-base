package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.DateConvertUtils;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

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

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
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

        @Bind(R.id.tv_name_request_type)
        TextView tv_name_request_type;

        @Bind(R.id.tv_time_homitam_data)
        TextView tv_time_homitam_data;

        @Bind(R.id.im_request_type)
        ImageView im_request_type;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(PatientRequestDisplayBean patientRequestDisplayBean) {
            if (patientRequestDisplayBean == null) return;
            PatientDisplayBean patientDisplayBean = patientRequestDisplayBean.patient;
            if (patientDisplayBean == null) return;


            if (CheckUtils.checkUrlNotNull(patientDisplayBean.getAvatar())) {
                ImageUrlUtils.picassoBySmallUrlCircle(context, patientDisplayBean.getAvatar(), ivPhotoHomeiteam);
            } else {
                ivPhotoHomeiteam.setImageResource(R.mipmap.ic_circle_head_green);
            }
            im_request_type.setImageResource(patientRequestDisplayBean.getRequestType().equals("远程医疗咨询")
                    ? R.mipmap.romote_service
                    : R.mipmap.exclusive_service);//图片更换
            tv_name_request_type.setText(patientRequestDisplayBean.getRequestType());
            tvNameHomeiteam.setText(patientDisplayBean.getFullName());
            tv_time_homitam_data.setText(DateUtils.GetDateText(patientRequestDisplayBean.requestDate, "yyyy-MM-dd"));

            tvTimeHomitam.setText(DateUtils.GetDateText(patientRequestDisplayBean.requestDate, "HH:mm"));
            tvTimeHomitam.setText(DateConvertUtils.getWeekOfDate(patientRequestDisplayBean.requestDate,null));
            tv_reason_homeiteam.setText(patientRequestDisplayBean.requestReason);

        }
    }
}
