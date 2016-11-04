package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/9/19.
 * <p/>
 * 描述：
 */
public class DoctorRequestAdapter extends CommonAdapter<DoctorRelationshipBean> {
    public DoctorRequestAdapter(Context context, List<DoctorRelationshipBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.doctor_request_item, null);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.civPhoto)
        CircleImageView civPhoto;
        @Bind(R.id.tv_doctor_request_name)
        TextView tv_doctor_request_name;
        @Bind(R.id.tv_doctor_request_comment)
        TextView tv_doctor_request_comment;

        @Bind(R.id.tv_doctor_request_action)
        TextView tv_doctor_request_action;
        @Bind(R.id.tv_doctor_request_action_gray)
        TextView tv_doctor_request_action_gray;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(DoctorRelationshipBean bean) {
            if (bean == null) return;
            tv_doctor_request_comment.setText(bean.getComment());
            if (bean.getStatus().equals("等待")) {
                tv_doctor_request_action.setVisibility(View.VISIBLE);
                tv_doctor_request_action.setText("同意");
                tv_doctor_request_action_gray.setVisibility(View.GONE);
            } else if (bean.status.equals("接受")) {
                tv_doctor_request_action_gray.setVisibility(View.VISIBLE);
                tv_doctor_request_action_gray.setText("已同意");
                tv_doctor_request_action.setVisibility(View.GONE);
            } else {
                tv_doctor_request_action_gray.setVisibility(View.VISIBLE);
                tv_doctor_request_action_gray.setText("已拒绝");
                tv_doctor_request_action.setVisibility(View.GONE);
            }
            DoctorDisplayBean displayBean = bean.getRelatedDoctor();
            if (displayBean == null) return;

            tv_doctor_request_name.setText(displayBean.fullName);

            if (CheckUtils.checkUrlNotNull(displayBean.avatar)) {
                ImageUrlUtils.picassoBySmallUrlCircle(context, displayBean.avatar, civPhoto);
            } else {
                civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
            }

        }
    }
}
