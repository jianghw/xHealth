package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.eventbus.JumpEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientInfoShipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.SingleChatBeanEvent;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;
import com.squareup.picasso.Picasso;
import com.youyou.zllibrary.widget.CommonAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 版权:    张磊
 * 作者:    张磊
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

        public void disposeData(final DoctorPatientRelationshipBean doctorPatientRelationshipBean) {

            if (doctorPatientRelationshipBean == null) return;
            PatientDisplayBean patientDisplayBean = doctorPatientRelationshipBean.getPatient();
            if (patientDisplayBean == null) return;
            Date dataOfBirth = patientDisplayBean.getDateOfBirth();
            if (dataOfBirth != null) {
                int age = DateUtils.getAge(dataOfBirth);
                tvAge.setText(context.getString(R.string.patient_age, age));
            } else {
                tvAge.setText(context.getString(R.string.patient_age_null));
            }
            String relationshipReason = doctorPatientRelationshipBean.getModifyRelationshipReason();
            tv_patient_type.setText(relationshipReason);

            tvName.setText(patientDisplayBean.getFullName());

            patient_item_news_tips.setVisibility(View.GONE);

            tvGender.setText(context.getString(R.string.patient_gender, patientDisplayBean.getGender()));
            if (!(TextUtils.isEmpty(patientDisplayBean.getAvatar()))) {
                Picasso.with(context)
                        .load(patientDisplayBean.getAvatar() + ImagSizeMode.imageSizeBeens[4].size)
                        .error(R.mipmap.ic_patient_error)
                        .placeholder(R.mipmap.photo_holder)
                        .into(civPhoto);
            } else {
                civPhoto.setImageResource(R.mipmap.ic_login_head_default);
            }

            boolean isNotActive = DateUtils.isActive(patientDisplayBean.isIsActive(), doctorPatientRelationshipBean.getEndDate());
            if (!isNotActive) civPhoto.setImageResource(R.mipmap.ic_avatar_over);
            civPhoto.setBorderColor(getColor(isNotActive ? R.color.civPhoto : R.color.texthomeiteam));
            tv_patient_type.setTextColor(getColor(isNotActive ? R.color.tv_patient_type : R.color.texthomeiteam));
            tvAge.setTextColor(getColor(isNotActive ? R.color.texthomeiteam : R.color.texthomeiteam));
            tvGender.setTextColor(getColor(isNotActive ? R.color.texthomeiteam : R.color.texthomeiteam));
            tvName.setTextColor(getColor(isNotActive ? R.color.tvName : R.color.texthomeiteam));

            btnConversation.setEnabled(isNotActive);

            btnConversation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new JumpEvent(Global.Jump.SingleChatActivity));
                    EventBus.getDefault().postSticky(new SingleChatBeanEvent(doctorPatientRelationshipBean));
                }
            });
            actionBarActivityContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new JumpEvent(Global.Jump.PatientInfoActivity));
                    EventBus.getDefault().postSticky(new PatientInfoShipBeanEvent(doctorPatientRelationshipBean));
                }
            });
        }

        private int getColor(int colorID) {
            return context.getResources().getColor(colorID);
        }

    }
}
