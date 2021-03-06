package com.kaurihealth.kaurihealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.FamilyMemberBean;
import com.kaurihealth.datalib.response_bean.FamilyMemberBeanWrapper;
import com.kaurihealth.datalib.response_bean.PatientDisplayBean;
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
public class FamilyMemberAdapter extends CommonAdapter<FamilyMemberBeanWrapper> {

    private final ItemClickBack itemClickBack;
    private ViewHolder viewHolder;

    public FamilyMemberAdapter(Context context, List<FamilyMemberBeanWrapper> list, ItemClickBack itemClickBack) {
        super(context, list);
        this.itemClickBack = itemClickBack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.family_member_item, null);
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
        //家庭成员相对布局
        @Bind(R.id.lay_family_item)
        RelativeLayout mLayFamilyMember;
        //头像
        @Bind(R.id.civ_photo)
        CircleImageView mCivPhoto;
        //家人名字
        @Bind(R.id.tv_name_patient)
        TextView mTvName;
        //关系
        @Bind(R.id.tv_relationship)
        TextView mTvRelationship;
        //"添加患者"按钮
        @Bind(R.id.btn_addPatient)
        Button mBtnAddPatient;
        //"已经是我的患者" 提示信息
        @Bind(R.id.tv_already_patient)
        TextView mTvAlreadyPatient;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        //处理数据
        @SuppressLint("SetTextI18n")
        void disposeData(FamilyMemberBeanWrapper familyWrapper, ItemClickBack itemClickBack) {
            if (familyWrapper == null) return;

            FamilyMemberBean bean = (FamilyMemberBean) familyWrapper.getBean();
            if (bean == null) return;
            PatientDisplayBean patientDisplayBean = bean.getPatient();
            mTvName.setText(patientDisplayBean.getFullName());
            ImageUrlUtils.picassoByUrlCircle(context, patientDisplayBean.getAvatar(), mCivPhoto);
            mTvRelationship.setText("关系:"+bean.getRelationship());   //关系

            mBtnAddPatient.setOnClickListener((v) -> {
                itemClickBack.onItemAddClick(patientDisplayBean.getPatientId());
            });

            String mStatus = familyWrapper.getStatus();
            if (mStatus.equals("普通")){
                setBtnHidden(true);
            }else if (mStatus.equals("专属")){
                setBtnHidden(true);
            }else if (mStatus.equals("转诊")){
                setBtnHidden(true);
            }else if (mStatus.equals("协作")){   //协作关系 : 后台没有给出具体解释
                setBtnHidden(false);
                mBtnAddPatient.setText("协作");
                mBtnAddPatient.setEnabled(false);
            }else if (mStatus.equals("路人")){

                setBtnHidden(false);
                mBtnAddPatient.setText("添加患者");
            }

            //医生本身也是患者家庭成员中的一员
            if (LocalData.getLocalData().getMyself().getDoctorId() == bean.getPatient().getPatientId() ){
                setBtnHidden(false);
                mBtnAddPatient.setText("本人");
                mBtnAddPatient.setEnabled(false);
            }

        }

        /**
         * 隐藏 "添加" 按钮
         */
        private void setBtnHidden(boolean b) {
            mTvAlreadyPatient.setVisibility(b ? View.VISIBLE : View.GONE);
            mBtnAddPatient.setVisibility(b ? View.GONE : View.VISIBLE);
        }
    }

    public interface ItemClickBack {

        void onItemAddClick(int doctorID);

    }
}
