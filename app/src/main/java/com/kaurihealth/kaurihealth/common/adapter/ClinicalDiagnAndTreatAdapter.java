package com.kaurihealth.kaurihealth.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.ClinicalDiagnosisAndTreatmentGroupIteam;
import com.kaurihealth.datalib.request_bean.bean.RecordDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.util.CommonAnimatedExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 版权:    张磊
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
public class ClinicalDiagnAndTreatAdapter extends CommonAnimatedExpandableListAdapter<ClinicalDiagnosisAndTreatmentGroupIteam> {
//    private int ErrorDefault = -10;
//    private int screenWidth = ErrorDefault;

    public ClinicalDiagnAndTreatAdapter(Context context, List<ClinicalDiagnosisAndTreatmentGroupIteam> data) {
        super(context, data);
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.medicalrecordchilditeam, null);
        }
        TextView tv_departmentname = (TextView) convertView.findViewById(R.id.tv_departmentname);
        TextView tv_date = (TextView) convertView.findViewById(R.id.tv_date);
        ImageView cv1 = (ImageView) convertView.findViewById(R.id.cv1);
        ImageView cv2 = (ImageView) convertView.findViewById(R.id.cv2);
        PatientRecordDisplayBean iteam = data.get(groupPosition).getList().get(childPosition);
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

        tv_departmentname.setText(iteam.department.departmentName);
//        DateUtil.GetDateText()
        tv_date.setText(iteam.recordDate.split("T")[0]);
        //判断是否是入院记录，院内治疗相关记录，出院记录
        if (iteam.category.equals("临床诊疗记录")) {
            if (iteam.record.recordType.equals("入院记录") || iteam.record.recordType.equals("院内治疗相关记录") || iteam.record.recordType.equals("出院记录")) {
                tv_departmentname.setText(iteam.hospital);
            }
        }
        return convertView;
    }


    @Override
    public int getRealChildrenCount(int groupPosition) {
        return data.get(groupPosition).getList().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ClinicalDiagnosisAndTreatmentGroupIteam iteam = data.get(groupPosition);
        View view = inflater.inflate(R.layout.medicalrecordgroupiteam, null);
        TextView tv_subject = (TextView) view.findViewById(R.id.tv_subject);
        TextView tv_num = (TextView) view.findViewById(R.id.tv_num);
        ImageView iv_question_arrow_iv = (ImageView) view.findViewById(R.id.question_arrow_iv);
        tv_subject.setText(iteam.getTitle());
        String strIteam = "/" + String.valueOf(iteam.getList().size());
        if (iteam.getList().size() > 0 && iteam.getList().size() < 10) {
            strIteam = "/0" + String.valueOf(iteam.getList().size());
        }
        tv_num.setText(strIteam);
        if (isExpanded) {
            iv_question_arrow_iv.setBackgroundResource(R.mipmap.log_arrow_image_s);
        } else {
            iv_question_arrow_iv.setBackgroundResource(R.mipmap.log_arrow_image_x);
        }
        return view;
    }
}
