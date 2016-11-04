package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.eventbus.PatientInfoShipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientJumpEvent;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 描述：jianghw 用来处理患者信息
 * 修订日期:
 */
public class PatientDocRelAdapter extends CommonAdapter<DoctorPatientRelationshipBean> {

    public PatientDocRelAdapter(Context context, List<DoctorPatientRelationshipBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.patient_iteam_gv, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        DoctorPatientRelationshipBean bean = list.get(position);

        viewHolder.disposeData(bean);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.civ_photo)
        CircleImageView civPhoto;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_gender)
        TextView tvGender;
        @Bind(R.id.tv_age)
        TextView tvAge;
        @Bind(R.id.action_bar_activity_content)
        LinearLayout actionBarActivityContent;
        @Bind(R.id.btn_conversation)
        Button btnConversation;
        @Bind(R.id.tv_patient_type)
        TextView tv_patient_type;
        @Bind(R.id.patient_item_news_tips)
        ImageView patient_item_news_tips;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void disposeData(final DoctorPatientRelationshipBean doctorPatientRelationshipBean) {
            if (doctorPatientRelationshipBean == null) return;
            String relationshipReason = doctorPatientRelationshipBean.getModifyRelationshipReason();
            tv_patient_type.setText(relationshipReason);
            patient_item_news_tips.setVisibility(View.GONE);

            PatientDisplayBean patientDisplayBean = doctorPatientRelationshipBean.getPatient();
            if (patientDisplayBean != null) {
                Date dataOfBirth = patientDisplayBean.getDateOfBirth();
                if (dataOfBirth != null) {
                    int age = DateUtils.calculateAge(dataOfBirth);
                    tvAge.setText(context.getString(R.string.patient_age, age));
                } else {
                    tvAge.setText(context.getString(R.string.patient_age_null));
                }
                tvName.setText(patientDisplayBean.getFullName());
                tvGender.setText(context.getString(R.string.patient_gender, patientDisplayBean.getGender()));
                boolean isNotActive = DateUtils.isActive(patientDisplayBean.isIsActive(), doctorPatientRelationshipBean.getEndDate());

                civPhoto.setImageResource(isNotActive ? R.mipmap.ic_circle_head_green : R.mipmap.ic_avatar_over);
                civPhoto.setBorderColor(getColor(isNotActive ? R.color.civPhoto : R.color.texthomeiteam));
                tv_patient_type.setTextColor(getColor(isNotActive ? R.color.tv_patient_type : R.color.texthomeiteam));
                tvAge.setTextColor(getColor(isNotActive ? R.color.texthomeiteam : R.color.texthomeiteam));
                tvGender.setTextColor(getColor(isNotActive ? R.color.texthomeiteam : R.color.texthomeiteam));
                tvName.setTextColor(getColor(isNotActive ? R.color.tvName : R.color.texthomeiteam));
                btnConversation.setEnabled(isNotActive);

                ImageUrlUtils.picassoByUrlCircle(context, patientDisplayBean.getAvatar(), civPhoto);

            }

            //条目内的消息按钮的点击监听
            btnConversation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new PatientJumpEvent(Global.Jump.SingleChatActivity, patientDisplayBean.getKauriHealthId()));
                }
            });

            //整个条目的点击监听
            actionBarActivityContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new PatientJumpEvent(Global.Jump.PatientInfoActivity, "0"));
                    EventBus.getDefault().postSticky(new PatientInfoShipBeanEvent(doctorPatientRelationshipBean));
                }
            });
        }

        private int getColor(int colorID) {
            return context.getResources().getColor(colorID);
        }

    }
}
