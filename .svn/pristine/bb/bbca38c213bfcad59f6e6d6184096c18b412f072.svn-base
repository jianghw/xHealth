package com.kaurihealth.kaurihealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.SearchPatientBean;
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
 * 病人搜索
 */
public class PatientSearchAdapter extends CommonAdapter<SearchPatientBean> {

    private IAddPatientListener addPatientListener;


    public PatientSearchAdapter(Context context, List<SearchPatientBean> list,IAddPatientListener addPatientListener) {
        super(context, list);
        this.addPatientListener = addPatientListener;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.list_item_search_patient, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        MainPagePatientDisplayBean bean = list.get(position).getItemsBean();
        boolean isAdd = list.get(position).isAdd();
        viewHolder.disposeData(bean, isAdd);
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

        @Bind(R.id.tv_referral_text)
        TextView tv_referral_text;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @SuppressLint("SetTextI18n")
        void disposeData(MainPagePatientDisplayBean displayBean, boolean isAdd) {
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
            }
            if (stringList.isEmpty()) stringList.add("暂无记录");

            mSearchSicknesses.setFlowLayout(stringList);

            mTvRecordDate.setText(DateUtils.unifiedFormatYearMonthDay(displayBean.getLatestRecordDate()));
            mTvRecordType.setText(CheckUtils.checkTextContent(displayBean.getRecordType()));
            tv_referral_text.setText(isAdd ? "已添加" : "添加患者");
            tv_referral_text.setTextColor(!isAdd ? context.getResources().getColor(R.color.red_btn_bg_color) :
                    context.getResources().getColor(R.color.gray_btn_bg_pressed_color));
            if (!isAdd){
                tv_referral_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addPatientListener.addPatient(displayBean.getPatientId());
                    }
                });
            }
        }
    }

    public interface IAddPatientListener{
        void addPatient(int patientId);
    }

}
