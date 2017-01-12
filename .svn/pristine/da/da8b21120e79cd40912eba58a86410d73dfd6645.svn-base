package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.DepartmentDisplayBean;
import com.kaurihealth.datalib.response_bean.PatientRecordDisplayDto;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.utilslib.image.RecordDocumentsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mip on 2016/12/23.
 *
 * Describe:通用的辅助检查adapter
 */

public class RecordAccessoryTestAdapter extends CommonAdapter<PatientRecordDisplayDto>{
    private ItemClickBack itemClickBack;

    public RecordAccessoryTestAdapter(Context context, List<PatientRecordDisplayDto> list) {
        super(context, list);
    }

    public RecordAccessoryTestAdapter(Context context, List<PatientRecordDisplayDto> list, ItemClickBack itemClickBack) {
        super(context, list);
        this.itemClickBack = itemClickBack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.listview_item_clinical_outpatient, null);
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
        @Bind(R.id.tv_subject)
        TextView mTvSubject;
        @Bind(R.id.img_count)
        ImageView mImgCount;
        @Bind(R.id.tv_date)
        TextView mTvDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(PatientRecordDisplayDto displayBean) {
            if (displayBean == null) return;
            DepartmentDisplayBean department = displayBean.getDepartment();
            if (department != null)
                mTvSubject.setText(CheckUtils.checkTextContent(department.getDepartmentName()));
            List<RecordDocumentsBean> recordDocuments = displayBean.getRecordDocuments();
            List<RecordDocumentsBean> documentsBeanList = new ArrayList<>();
            if (recordDocuments != null) {
                for (RecordDocumentsBean bean : recordDocuments) {
                    if (!bean.isIsDeleted()) {
                        documentsBeanList.add(bean);
                    }
                }
            }
            mImgCount.setVisibility(documentsBeanList == null || documentsBeanList.isEmpty() ? View.GONE : View.VISIBLE);
            if (documentsBeanList.size() > 0) documentsBeanList.clear();

            mTvDate.setText(DateUtils.unifiedFormatYearMonthDay(displayBean.getCreatedDate()));
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
