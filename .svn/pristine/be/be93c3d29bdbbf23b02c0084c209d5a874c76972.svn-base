package com.kaurihealth.kaurihealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.response_bean.DoctorDisplayBean;
import com.kaurihealth.datalib.response_bean.KauriHealthPendingConsultationReferralBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 全部病历
 */
public class ProcessedRecordsAdapter extends CommonAdapter<KauriHealthPendingConsultationReferralBean> {

    private ItemClickBack itemClickBack;

    public ProcessedRecordsAdapter(Context context, List<KauriHealthPendingConsultationReferralBean> list) {
        super(context, list);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.list_item_processed_records, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        KauriHealthPendingConsultationReferralBean bean = list.get(position);
        viewHolder.disposeData(bean);
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    public void setOnItemClickListener(ItemClickBack itemClickBack) {
        this.itemClickBack = itemClickBack;
    }

    class ViewHolder {
        @Bind(R.id.tv_patient_name)
        TextView mTvPatientName;
        @Bind(R.id.img_gender)
        ImageView mImgGender;
        @Bind(R.id.tv_patient_age)
        TextView mTvPatientAge;
        @Bind(R.id.tv_type)
        TextView mTvDoctorType;
        @Bind(R.id.tv_phone)
        TextView mTvDisease;
        @Bind(R.id.tv_address)
        TextView mTvSource;
        @Bind(R.id.tv_address_title)
        TextView mTvSourceTitle;
        @Bind(R.id.tv_date)
        TextView mTvDate;
        @Bind(R.id.tv_communication)
        TextView mTvCommunication;
        @Bind(R.id.tv_refuse)
        TextView mTvRefuse;
        @Bind(R.id.tv_accept)
        TextView mTvAccept;
        @Bind(R.id.cardView)
        CardView mCardView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        void disposeData(KauriHealthPendingConsultationReferralBean displayBean) {
            if (displayBean == null) return;
            String patientFullName = displayBean.getPatientFullName();
            mTvPatientName.setText(CheckUtils.checkTextContent(patientFullName));

            mImgGender.setImageResource(displayBean.getGender().contains("男") ? R.drawable.gender_icon : R.drawable.gender_women);
            mTvPatientAge.setText(DateUtils.calculateAge(displayBean.getDateOfBirth()) + "岁");
            mTvDoctorType.setText(CheckUtils.checkTextContent(displayBean.getReferralType()));

            List<SicknessesBean> sickList = displayBean.getSicknesses();
            StringBuilder stringBuilder = new StringBuilder();
            if (sickList != null && !sickList.isEmpty()) {
                for (SicknessesBean bean : sickList) {
                    stringBuilder.append(bean.getSicknessName());
                    stringBuilder.append("/");
                }
            } else {
                stringBuilder.append("暂无记录");
                stringBuilder.append("/");
            }
            String disease = stringBuilder.toString();
            String newDis = disease.substring(0, disease.length() - 1);
            mTvDisease.setText(CheckUtils.checkTextContent(newDis));

            mTvDate.setText(DateUtils.unifiedFormatYearMonthDay(displayBean.getDate()));

            DoctorDisplayBean myBean = LocalData.getLocalData().getMyself();
            String mKauriHealthId = "";
            if (myBean != null) mKauriHealthId = myBean.getKauriHealthId();
            String mSourceDoctorKauriHealthId = displayBean.getSourceDoctorKauriHealthId();
            String mDestinationDoctorKauriHealthId = displayBean.getDestinationDoctorKauriHealthId();
            String kauriHealthId = mKauriHealthId.equals(mSourceDoctorKauriHealthId) ? mDestinationDoctorKauriHealthId : mSourceDoctorKauriHealthId;
            boolean isSource = mKauriHealthId.equals(mSourceDoctorKauriHealthId);
            mTvSource.setText(isSource?CheckUtils.checkTextContent(displayBean.getDestinationDoctorFullName()):CheckUtils.checkTextContent(displayBean.getSourceDoctorFullName()));
            mTvSourceTitle.setText(isSource?"被转诊医师：":"转诊医师：");

            mTvCommunication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickBack.onItemChatClick(kauriHealthId);
                }
            });

            mTvAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickBack.onItemAcceptClick(displayBean.getPatientRequestId());
                }
            });
            mTvRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickBack.onItemRefuseClick(displayBean.getPatientRequestId());
                }
            });

            if (displayBean.getReferralType().contains("对方")) {
                mTvAccept.setVisibility(View.GONE);
                mTvRefuse.setVisibility(View.GONE);
            } else {
                mTvAccept.setVisibility(View.VISIBLE);
                mTvRefuse.setVisibility(View.VISIBLE);
            }
        }
    }

    public interface ItemClickBack {
        //聊天
        void onItemAcceptClick(int id);

        //添加医生
        void onItemRefuseClick(int id);

        //医生详情
        void onItemChatClick(String id);
    }

}
