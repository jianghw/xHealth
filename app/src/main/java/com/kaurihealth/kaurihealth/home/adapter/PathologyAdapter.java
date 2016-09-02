package com.kaurihealth.kaurihealth.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.RecordDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class PathologyAdapter extends CommonAdapter<PatientRecordDisplayBean> {
    public PathologyAdapter(Context context, List<PatientRecordDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            View view = inflater.inflate(R.layout.medicalrecordchilditeam, null);
            convertView = view;
        }
        ImageView cv1 = (ImageView) convertView.findViewById(R.id.cv1);
        ImageView cv2 = (ImageView) convertView.findViewById(R.id.cv2);
        PatientRecordDisplayBean iteam = list.get(position);
        List<RecordDocumentDisplayBean> recordDocuments = iteam.recordDocuments;
        if (recordDocuments != null) {
            List<RecordDocumentDisplayBean> temp = new ArrayList<>();
            for (RecordDocumentDisplayBean iteamOne : recordDocuments) {
                if (!iteamOne.isDeleted) {
                    temp.add(iteamOne);
                }
            }
            if (temp.size() == 0) {
                cv1.setVisibility(View.INVISIBLE);
                cv2.setVisibility(View.INVISIBLE);
            } else if (temp.size() == 1) {
                cv1.setVisibility(View.INVISIBLE);
                cv2.setVisibility(View.VISIBLE);
            } else {
                cv1.setVisibility(View.VISIBLE);
                cv2.setVisibility(View.VISIBLE);
            }
        } else {
            cv1.setVisibility(View.INVISIBLE);
            cv2.setVisibility(View.INVISIBLE);
        }
        TextView tv_departmentname = (TextView) convertView.findViewById(R.id.tv_departmentname);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        tv_departmentname.setText(iteam.department.departmentName);
        tv_date.setText(iteam.recordDate.split("T")[0]);
        return convertView;
    }
}
