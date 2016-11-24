package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.PatientRequestDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.DateConvertUtils;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/10/24.
 * <p/>
 * Describe:
 */

public class ReferralPatientRequestAdapter extends CommonAdapter<PatientRequestDisplayBean> {

    public ReferralPatientRequestAdapter(Context context, List<PatientRequestDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.referral_patient_request_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.civPhoto)
        CircleImageView civPhoto;
        @Bind(R.id.tv_referral_request_name)
        TextView tv_referral_request_name;
        @Bind(R.id.tv_referral_request_comment)
        TextView tv_referral_request_comment;
        @Bind(R.id.tv_time)
        TextView tv_time;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(PatientRequestDisplayBean bean) {
            PatientDisplayBean patient = bean.getPatient();
            if (CheckUtils.checkUrlNotNull(patient.getAvatar())) {
                ImageUrlUtils.picassoBySmallUrlCircle(context, patient.getAvatar(), civPhoto);
            } else {
                civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
            }
            tv_referral_request_name.setText(patient.getFullName());
            tv_referral_request_comment.setText(TextUtils.isEmpty(bean.getRequestReason())? "您好，我预约转诊服务。" : bean.getRequestReason());
            tv_referral_request_comment.setText(TextUtils.isEmpty(bean.getConsultationReferral().getComment())?"您好，我预约转诊服务。":bean.getConsultationReferral().getComment());
//            tv_time.setText(DateUtils.GetDateText(bean.getRequestDate(), "HH:mm"));
            tv_time.setText(DateConvertUtils.getWeekOfDate(bean.getRequestDate(),null));
        }
    }
}
