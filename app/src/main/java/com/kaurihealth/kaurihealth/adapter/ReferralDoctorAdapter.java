package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/10/24.
 */

public class ReferralDoctorAdapter extends CommonAdapter<DoctorRelationshipBean> {
    private List<Integer> doctorIdlist;

    public ReferralDoctorAdapter(Context context, List<DoctorRelationshipBean> list, List<Integer> doctorIdlist) {
        super(context, list);
        this.doctorIdlist = doctorIdlist;
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

        public void setInfo(DoctorRelationshipBean info, int position) {
            if (info.getRelatedDoctor() != null) {
                DoctorDisplayBean doctorDisplayBean = info.getRelatedDoctor();
                if (CheckUtils.checkUrlNotNull(doctorDisplayBean.getAvatar())) {
                    Picasso.with(context).load(doctorDisplayBean.getAvatar())
                            .placeholder(R.mipmap.ic_circle_head_green)
                            .error(R.mipmap.ic_circle_head_green)
                            .into(civPhoto);
                }else {
                    civPhoto.setImageResource(R.mipmap.ic_circle_head_green);
                }
                tv_referral_request_name.setText(doctorDisplayBean.getFullName());
                tv_referral_doctor_department.setText(doctorDisplayBean.getDoctorInformations() != null
                        ? doctorDisplayBean.getDoctorInformations().getDepartment() != null
                        ? doctorDisplayBean.getDoctorInformations().getDepartment().getDepartmentName()
                        : "暂无" : "暂无");
                tv_referral_doctor_professional.setText(doctorDisplayBean.getHospitalTitle());
                tv_referral_doctor_professional_Two.setText(doctorDisplayBean.getMentorshipTitle() != null
                        ? doctorDisplayBean.getMentorshipTitle() : "暂无");
                tv_referral_doctor_organization.setText(doctorDisplayBean.getDoctorInformations().getHospitalName() != null
                        ? doctorDisplayBean.getDoctorInformations().getHospitalName() != null
                        ? doctorDisplayBean.getDoctorInformations().getHospitalName()
                        : "暂无" : "暂无");
                tv_referral_checkbox.setOnCheckedChangeListener((compoundButton, isChecked) -> {

                    if (!isChecked){
                        info.type = 0;
                    }
                    if (isChecked) {
                        info.type = 1;//表示被点击
                        if (!(doctorIdlist.contains(info.getRelatedDoctor().getDoctorId()))){
                            doctorIdlist.add(info.getRelatedDoctor().getDoctorId());
                        }
                    } else if (doctorIdlist.contains(info.getRelatedDoctor().getDoctorId())) {
                        doctorIdlist.remove(doctorIdlist.indexOf(info.getRelatedDoctor().getDoctorId()));
                    }
                });

                //防止checkBox混乱的问题
                if (info.type == 1) {
                    tv_referral_checkbox.setChecked(true);
                } else {
                    tv_referral_checkbox.setChecked(false);
                }
            }
        }
    }
}
