package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorInformationDisplayBean;
import com.kaurihealth.datalib.response_bean.DoctorPatientRelationshipBean;
import com.kaurihealth.datalib.response_bean.RelationshipBeanWrapper;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述：wwj 用来处理医生团队信息
 * 修订日期:
 */
public class DoctorTeamAdapter extends CommonAdapter<RelationshipBeanWrapper> {

    private final ItemClickBack itemClickBack;

    public DoctorTeamAdapter(Context context, List<RelationshipBeanWrapper> list, ItemClickBack itemClickBack) {
        super(context, list);
        this.itemClickBack = itemClickBack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.doctor_item_gv, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.disposeData(list.get(position), itemClickBack);
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }


    //viewHolder
    class ViewHolder {
        @Bind(R.id.civ_photo)
        CircleImageView mCivPhoto;
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

        private ItemClickBack itemClickBack;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        //处理数据
        void disposeData(RelationshipBeanWrapper doctorWrapper, ItemClickBack itemClickBack) {
            if (doctorWrapper == null) return;
            this.itemClickBack = itemClickBack;
            DoctorPatientRelationshipBean relationshipBean = (DoctorPatientRelationshipBean) doctorWrapper.getBean();
            if (relationshipBean == null) return;

            String mStatus = doctorWrapper.getStatus();
            if (mStatus.equals("接受")) {

                setBtnHidden(true);

            } else if (mStatus.equals("拒绝")) {

                mBtnAdd.setBackgroundResource(R.drawable.bg_patientitem_add_new);
                setBtnHidden(false);
                mBtnAdd.setText("拒绝");
                mBtnAdd.setEnabled(false);
            } else if (mStatus.equals("等待")) {

                setBtnHidden(false);
                mBtnAdd.setText("等待审核");
                mBtnAdd.setBackgroundResource(R.drawable.bg_patientitem_add);
                mBtnAdd.setEnabled(false);

            } else if (mStatus.equals("路人")) {

                mBtnAdd.setBackgroundResource(R.drawable.bg_patientitem_add_new);
                setBtnHidden(false);
                mBtnAdd.setEnabled(true);
                mBtnAdd.setText("添加");
            } else {

                setBtnHidden(false);
                mBtnAdd.setText("未知");
                mBtnAdd.setEnabled(false);

            }

            if (LocalData.getLocalData().getMyself().getDoctorId() == relationshipBean.getDoctor().getDoctorId()) {
                mBtnAdd.setBackgroundResource(R.drawable.bg_patientitem_add_new);
                setBtnHidden(false);
                mBtnAdd.setText("本人");
                mBtnAdd.setEnabled(false);
            }

            DoctorDisplayBean doctorDisplayBean = relationshipBean.getDoctor();
            if (doctorWrapper != null) {
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
            mBtnConversation.setOnClickListener((v) -> {//聊天
                if (doctorDisplayBean != null)
                    itemClickBack.onItemChatClick(doctorDisplayBean.getKauriHealthId());
            });

            mBtnAdd.setOnClickListener((v) -> {
                if (doctorDisplayBean != null)
                    itemClickBack.onItemAddClick(doctorDisplayBean.getDoctorId());
            });

            mLayDoctorItem.setOnClickListener((v) -> {//跳转详情页面
                itemClickBack.onItemInfoClick(relationshipBean,mBtnConversation.getVisibility()==View.GONE?mBtnAdd.getText().toString():"接受");
            });
        }

        private void setBtnHidden(boolean b) {
            mBtnConversation.setVisibility(b ? View.VISIBLE : View.GONE);
            mBtnAdd.setVisibility(b ? View.GONE : View.VISIBLE);
        }
    }



    public interface ItemClickBack {
        //聊天
        void onItemChatClick(String kauriHealthId);

        //添加医生
        void onItemAddClick(int doctorID);

        //医生详情
        void onItemInfoClick(DoctorPatientRelationshipBean doctorWrapper,String type);
    }
}
