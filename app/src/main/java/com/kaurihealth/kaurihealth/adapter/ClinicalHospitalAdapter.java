package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.HospitalizationBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.datalib.response_bean.RecordBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/10/24.
 * <p/>
 * Describe: 住院记录
 */

public class ClinicalHospitalAdapter extends CommonAdapter<PatientRecordDisplayDto> {

    private ItemClickBack itemClickBack;

    public ClinicalHospitalAdapter(Context context, List<PatientRecordDisplayDto> list) {
        super(context, list);
    }

    public ClinicalHospitalAdapter(Context context, List<PatientRecordDisplayDto> list, ItemClickBack itemClickBack) {
        super(context, list);
        this.itemClickBack = itemClickBack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.listview_item_clinical_hospital, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.tv_hospital)
        TextView mTvHospital;
        @Bind(R.id.img_count)
        ImageView mImgCount;
        @Bind(R.id.tv_date)
        TextView mTvDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(PatientRecordDisplayDto displayBean) {
            if (displayBean == null) return;
            RecordBean recordBean = displayBean.getRecord();
            String hospital = displayBean.getHospital();
            DepartmentDisplayBean department = displayBean.getDepartment();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CheckUtils.checkTextContent(hospital));
            stringBuilder.append("/");
            if (department != null)
                stringBuilder.append(CheckUtils.checkTextContent(department.getDepartmentName()));
            mTvHospital.setText(stringBuilder.toString());

            List<HospitalizationBean> hospitalizations = displayBean.getHospitalization();
            mImgCount.setVisibility(View.GONE);
            if (!hospitalizations.isEmpty()) {
                for (HospitalizationBean hospitalizationBean : hospitalizations) {
                    if (hospitalizationBean != null && hospitalizationBean.getHospitalizationType().contains("出院")) {
                        mImgCount.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }

            mTvDate.setText(DateUtils.unifiedFormatYearMonthDay(displayBean.getRecordDate()));
        }

//        @OnClick({R.id.tv_delete})
//        public void itemDelete() {
//            itemClickBack.onItemClick(1);
//        }
    }

    public interface ItemClickBack {
        void onItemClick(int referralMessageAlertId);
    }
}
