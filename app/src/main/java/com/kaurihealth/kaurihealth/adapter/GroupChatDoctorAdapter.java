package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
 * Created by jianghw on 2016/10/24.
 * <p/>
 * Describe:
 */

public class GroupChatDoctorAdapter extends CommonAdapter<DoctorRelationshipBean> {

    public GroupChatDoctorAdapter(Context context, List<DoctorRelationshipBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.referral_doctor_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position), position);
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
        @Bind(R.id.tv_referral_doctor_department)
        TextView tv_referral_doctor_department;
        @Bind(R.id.tv_referral_doctor_professional)
        TextView tv_referral_doctor_professional;
        @Bind(R.id.tv_referral_doctor_professional_Two)
        TextView tv_referral_doctor_professional_Two;
        @Bind(R.id.tv_referral_doctor_organization)
        TextView tv_referral_doctor_organization;
        @Bind(R.id.tv_referral_checkbox)
        CheckBox tv_referral_checkbox;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(DoctorRelationshipBean doctorRelationshipBean, int position) {
            DoctorDisplayBean relatedDoctor = doctorRelationshipBean.getRelatedDoctor();
            if (relatedDoctor != null) {
                if (CheckUtils.checkUrlNotNull(relatedDoctor.getAvatar())) {
                    ImageUrlUtils.picassoByUrlCircle(context,relatedDoctor.getAvatar(),civPhoto);
                } else {
                    ImageUrlUtils.picassoByUrlCircle(context,civPhoto);
                }

                tv_referral_request_name.setText(relatedDoctor.getFullName());
                tv_referral_doctor_department.setText(relatedDoctor.getDoctorInformations() != null
                        ? relatedDoctor.getDoctorInformations().getDepartment() != null
                        ? relatedDoctor.getDoctorInformations().getDepartment().getDepartmentName()
                        : "暂无" : "暂无");

                tv_referral_doctor_professional.setText(relatedDoctor.getHospitalTitle() != null ? relatedDoctor.getHospitalTitle() : "暂无");
                tv_referral_doctor_professional_Two.setText(relatedDoctor.getMentorshipTitle() != null ? relatedDoctor.getMentorshipTitle() : "暂无");
                tv_referral_doctor_organization.setText(relatedDoctor.getDoctorInformations().getHospitalName() != null
                        ? relatedDoctor.getDoctorInformations().getHospitalName() != null
                        ? relatedDoctor.getDoctorInformations().getHospitalName()
                        : "暂无" : "暂无");

                tv_referral_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        doctorRelationshipBean.isCheck=isChecked;
                    }
                });

                tv_referral_checkbox.setChecked(doctorRelationshipBean.isCheck);
            }
        }
    }
}
