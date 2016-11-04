package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.eventbus.DoctorJumpEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述：jianghw 用来处理医生信息
 * 修订日期:
 */
public class DoctorCooperationAdapter extends CommonAdapter<DoctorRelationshipBean> {

    public DoctorCooperationAdapter(Context context, List<DoctorRelationshipBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.doctor_iteam_gv, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        DoctorRelationshipBean bean = list.get(position);
        viewHolder.disposeData(bean);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.civ_photo)
        CircleImageView mCivPhoto;
        @Bind(R.id.patient_item_news_tips)
        ImageView mPatientItemNewsTips;
        @Bind(R.id.tv_name)
        TextView mTvName;
        @Bind(R.id.tv_practiceField)
        TextView mTvPracticeField;
        @Bind(R.id.tv_hospital)
        TextView mTvHospital;
        @Bind(R.id.btn_conversation)
        Button mBtnConversation;
        @Bind(R.id.lay_doctor_item)
        LinearLayout mLayDoctorItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void disposeData(DoctorRelationshipBean doctorRelationshipBean) {
            if (doctorRelationshipBean == null) return;
            boolean isNotActive = doctorRelationshipBean.getStatus().equals("接受");
            mCivPhoto.setImageResource(isNotActive ? R.mipmap.ic_circle_head_green : R.mipmap.ic_avatar_over);
            mCivPhoto.setBorderColor(getColor(isNotActive ? R.color.color_enable_green : R.color.color_label_back));
            mTvPracticeField.setTextColor(getColor(isNotActive ? R.color.tv_patient_type : R.color.texthomeiteam));
            mTvName.setTextColor(getColor(isNotActive ? R.color.tvName : R.color.texthomeiteam));
            mBtnConversation.setEnabled(isNotActive);

            DoctorDisplayBean doctorDisplayBean = doctorRelationshipBean.getRelatedDoctor();
            if (doctorDisplayBean != null) {
                mTvName.setText(doctorDisplayBean.getFullName());
                ImageUrlUtils.picassoByUrlCircle(context, doctorDisplayBean.getAvatar(), mCivPhoto);

                DoctorInformationDisplayBean doctorInformations = doctorDisplayBean.getDoctorInformations();
                if (doctorInformations != null) {
                    mTvHospital.setText(TextUtils.isEmpty(doctorInformations.getHospitalName()) ? context.getResources().getString(R.string.being_no) : doctorInformations.getHospitalName());
                    DepartmentDisplayBean department = doctorInformations.getDepartment();
                    if (department != null) {
                        mTvPracticeField.setText(TextUtils.isEmpty(department.getDepartmentName()) ? context.getResources().getString(R.string.being_no) : department.getDepartmentName());
                    } else {
                        mTvPracticeField.setText(context.getResources().getString(R.string.being_no));
                    }
                }
            }

            mBtnConversation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//聊天
                    if (doctorDisplayBean != null)
                        EventBus.getDefault().post(new DoctorJumpEvent(Global.Jump.SingleChatActivity, doctorDisplayBean.getKauriHealthId()));
                }
            });
            mLayDoctorItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//跳转详情页面
                    EventBus.getDefault().post(new DoctorJumpEvent(Global.Jump.DoctorInfoActivity, "0"));
                    EventBus.getDefault().postSticky(new DoctorRelationshipBeanEvent(doctorRelationshipBean));
                }
            });
        }

        private int getColor(int colorID) {
            return context.getResources().getColor(colorID);
        }
    }
}
