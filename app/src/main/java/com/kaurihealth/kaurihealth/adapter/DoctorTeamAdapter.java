package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.ConsultationReferralBean;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.DoctorRelationshipBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.eventbus.DoctorInfoBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorJumpEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorPatientRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorRelationshipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.DoctorTeamJumpEvent;
import com.kaurihealth.kaurihealth.eventbus.PatientInfoShipBeanEvent;
import com.kaurihealth.kaurihealth.eventbus.VerificationJumpEvent;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述：wjj 用来处理医生团队信息
 * 修订日期:
 */
public class DoctorTeamAdapter extends CommonAdapter<DoctorPatientRelationshipBean> {
    public List<Integer> sourceDoctorId = new ArrayList<>();   //发起请求人sourceDoctorId

    public DoctorTeamAdapter(Context context, List<DoctorPatientRelationshipBean> list) {
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
        DoctorPatientRelationshipBean bean = list.get(position);
        viewHolder.disposeData(bean);
        return convertView;
    }

    class ViewHolder {
        //头像
        @Bind(R.id.civ_photo)
        CircleImageView mCivPhoto;
        @Bind(R.id.patient_item_news_tips)
        ImageView mPatientItemNewsTips;
        @Bind(R.id.tv_name)
        TextView mTvName;
        //专业领域（内科系统...）
        @Bind(R.id.tv_practiceField)
        TextView mTvPracticeField;
        //医院
        @Bind(R.id.tv_hospital)
        TextView mTvHospital;
        //留言
        @Bind(R.id.btn_conversation)
        Button mBtnConversation;
        // “添加”按钮
        @Bind(R.id.btn_add)
        Button mBtnAdd;
        @Bind(R.id.lay_doctor_item)
        LinearLayout mLayDoctorItem;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        void disposeData(DoctorPatientRelationshipBean doctorRelationshipBean) {
            if (doctorRelationshipBean == null) return;
//            boolean isNotActive = doctorRelationshipBean.getStatus().equals("接受");
//            mCivPhoto.setImageResource(isNotActive ? R.mipmap.ic_circle_head_green : R.mipmap.ic_avatar_over);
//
//            mCivPhoto.setBorderColor(getColor(isNotActive ? R.color.color_enable_green : R.color.color_label_back));
//            mTvPracticeField.setTextColor(getColor(isNotActive ? R.color.tv_patient_type : R.color.texthomeiteam));
//            mTvName.setTextColor(getColor(isNotActive ? R.color.tvName : R.color.texthomeiteam));
//            mBtnConversation.setEnabled(isNotActive);

            boolean isFriend =doctorRelationshipBean.getDoctorPatientId()!=0;
            mBtnConversation.setVisibility(isFriend?View.VISIBLE:View.GONE);
            mBtnAdd.setVisibility(isFriend?View.GONE:View.VISIBLE);
            DoctorDisplayBean doctorDisplayBean = doctorRelationshipBean.getDoctor();
            if (doctorRelationshipBean != null) {
                mTvName.setText(doctorDisplayBean.getFullName());
                    ImageUrlUtils.picassoBySmallUrlCircle(context, doctorDisplayBean.getAvatar(), mCivPhoto);

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

            mBtnConversation.setOnClickListener(/*new View.OnClickListener() {
                        @Override
                        public void onClick*/(/*View*/ v)-> {//聊天
                if (doctorDisplayBean != null)
                    EventBus.getDefault().post(new DoctorTeamJumpEvent(Global.Jump.SingleChatActivity, doctorDisplayBean.getKauriHealthId()));
                /*}*/
            });
            mBtnAdd.setOnClickListener(/*new View.OnClickListener() {

                         @Override
                        public void onClick*/(/*View*/ v)-> {//聊天
                if (doctorDisplayBean != null)
                    EventBus.getDefault().post(new VerificationJumpEvent(Global.Jump.VerificationActivity));  //跳转到发送确认消息界面
                EventBus.getDefault().postSticky(new VerificationJumpEvent(Global.Jump.VerificationActivity));  //跳转到确认界面

            });
            mLayDoctorItem.setOnClickListener(/*new View.OnClickListener() {
                @Override
                public void onClick*/(/*View*/ v) -> {//跳转详情页面
                    EventBus.getDefault().post(new DoctorTeamJumpEvent(Global.Jump.DoctorInfoActivity, "0")); //自定义事件:页面跳转事件--发送事件
                    EventBus.getDefault().postSticky(new DoctorPatientRelationshipBeanEvent(doctorRelationshipBean)); //发送bean数据 DoctorInfoActivity用的
                  //  EventBus.getDefault().postSticky(new DoctorInfoBeanEvent(doctorRelationshipBean)); //发送bean数据 DoctorInfoActivity用的
                  //  EventBus.getDefault().postSticky(new DoctorInfoBeanEvent(Global.Jump.SingleChatActivity, doctorDisplayBean.getKauriHealthId())); //发送bean数据 DoctorInfoActivity用的-->聊天
                /*}*/
            });
        }

        private int getColor(int colorID) {
            return context.getResources().getColor(colorID);
        }
    }
}
