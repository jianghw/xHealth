package com.kaurihealth.kaurihealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.MainPagePatientDisplayBean;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.widget.FlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 转诊病人
 */
public class PatientReferralAdapter extends CommonAdapter<MainPagePatientDisplayBean> {

    public PatientReferralAdapter(Context context, List<MainPagePatientDisplayBean> list) {
        super(context, list);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.list_item_referral_patient, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        MainPagePatientDisplayBean bean = list.get(position);
        viewHolder.disposeData(bean);
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.tv_patient_name)
        TextView mTvPatientName;
        @Bind(R.id.img_gender)
        ImageView mImgGender;
        @Bind(R.id.tv_patient_age)
        TextView mTvPatientAge;
        @Bind(R.id.search_sicknesses)
        FlowLayout mSearchSicknesses;
        @Bind(R.id.tv_recordDate)
        TextView mTvRecordDate;
        @Bind(R.id.tv_recordType)
        TextView mTvRecordType;
        @Bind(R.id.cardView)
        CardView mCardView;

        @Bind(R.id.tv_referral_checkbox)
        CheckBox tv_referral_checkbox;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        void disposeData(MainPagePatientDisplayBean displayBean) {
            if (displayBean == null) return;
            String patientFullName = displayBean.getPatientFullName();
            mTvPatientName.setText(CheckUtils.checkTextContent(patientFullName));

            mImgGender.setImageResource(displayBean.getGender().contains("男") ? R.drawable.gender_icon : R.drawable.gender_women);
            mTvPatientAge.setText(DateUtils.calculateAge(displayBean.getDateOfBirth()) + "岁");

            List<SicknessesBean> beanList = displayBean.getSicknesses();
            List<String> stringList = new ArrayList<>();

            if (beanList != null && !beanList.isEmpty()) {
                for (SicknessesBean bean : beanList) {
                    stringList.add(bean.getSicknessName());
                }
            } else if (beanList != null && beanList.size() == 1) {
                SicknessesBean bean = beanList.get(0);
//                if (bean != null && bean.getSicknessName().contains("诊断无")) {
//                    mTvDoctorName.setVisibility(View.GONE);
//                    mTvDoctorNameTitle.setVisibility(View.GONE);
//                } else {
//                    mTvDoctorName.setVisibility(View.VISIBLE);
//                    mTvDoctorNameTitle.setVisibility(View.VISIBLE);
//                }
            }

            if (stringList.isEmpty()) stringList.add("暂无记录");

            mSearchSicknesses.setFlowLayout(stringList);

            mTvRecordDate.setText(DateUtils.unifiedFormatYearMonthDay(displayBean.getLatestRecordDate()));
            mTvRecordType.setText(CheckUtils.checkTextContent(displayBean.getRecordType()));

            tv_referral_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    displayBean.isCheck = isChecked;
                }
            });

            tv_referral_checkbox.setChecked(displayBean.isCheck);
        }
    }

}
