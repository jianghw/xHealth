package com.kaurihealth.kaurihealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
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
 * 全部病历
 */
public class AllMedicalRecordsAdapter extends CommonAdapter<MainPagePatientDisplayBean> {

    public AllMedicalRecordsAdapter(Context context, List<MainPagePatientDisplayBean> list) {
        super(context, list);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.list_item_all_medical_records, parent, false);
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
        @Bind(R.id.tv_type)
        TextView mTvDoctorName;
        @Bind(R.id.tv_tv_doctor_name_title)
        TextView mTvDoctorNameTitle;
        @Bind(R.id.search_sicknesses)
        FlowLayout mSearchSicknesses;
        @Bind(R.id.tv_recordDate)
        TextView mTvRecordDate;
        @Bind(R.id.tv_recordType)
        TextView mTvRecordType;
        @Bind(R.id.cardView)
        CardView mCardView;

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

            mTvDoctorName.setText(CheckUtils.checkTextContent(displayBean.getDoctorFullName()));

            List<SicknessesBean> beanList = displayBean.getSicknesses();
            List<String> stringList = new ArrayList<>();
            if (beanList != null && !beanList.isEmpty()) {
                for (SicknessesBean bean : beanList) {
                    stringList.add(bean.getSicknessName());
                }
            }

            if (stringList.isEmpty()) {
                stringList.add("暂无记录");
                mTvDoctorName.setVisibility(View.GONE);
                mTvDoctorNameTitle.setVisibility(View.GONE);
            } else if (stringList.size() == 1) {
                String bean = stringList.get(0);
                mTvDoctorName.setVisibility(bean.contains("诊断无") ? View.GONE : View.VISIBLE);
                mTvDoctorNameTitle.setVisibility(bean.contains("诊断无") ? View.GONE : View.VISIBLE);
            } else {
                mTvDoctorName.setVisibility(View.VISIBLE);
                mTvDoctorNameTitle.setVisibility(View.VISIBLE);
            }

            mSearchSicknesses.setFlowLayout(stringList);

            mTvRecordDate.setText(DateUtils.unifiedFormatYearMonthDay(displayBean.getLatestRecordDate()));
            mTvRecordType.setText(CheckUtils.checkTextContent(displayBean.getRecordType()));
        }
    }

    private int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
