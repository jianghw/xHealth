package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.PatientRecordDisplayBean;
import com.kaurihealth.datalib.response_bean.RecordDocumentsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.date.DateUtils;

import java.util.List;

import rx.Observable;

/**
 * 描述：
 * 修订日期:
 */
public class ClinicalMedicalBeanAdapter extends BaseAExpandableListAdapter<ClinicalMedicalBeanItem> {

    public ClinicalMedicalBeanAdapter(Context context, List<ClinicalMedicalBeanItem> data) {
        super(context, data);
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return data.get(groupPosition).getList().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getList().get(childPosition);
    }

    /**
     * 父布局
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        ClinicalMedicalBeanItem beanItem = data.get(groupPosition);
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.elist_group_iteam, parent, false);
            holder.tv_subject = (TextView) convertView.findViewById(R.id.tv_subject);
            holder.tv_num = (TextView) convertView.findViewById(R.id.tv_num);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_arrow);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.tv_subject.setText(beanItem.getTitle());
        String itemSize = String.format("%02d", beanItem.getList().size());
        holder.tv_num.setText("/" + itemSize);
        holder.imageView.setBackgroundResource(isExpanded ? R.mipmap.ic_arrow_up : R.mipmap.ic_arrow_down);
        return convertView;
    }

    private static class GroupHolder {
        TextView tv_subject;
        TextView tv_num;
        ImageView imageView;
    }

    /**
     * 子布局
     */
    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        PatientRecordDisplayBean childItem = (PatientRecordDisplayBean) getChild(groupPosition, childPosition);
        if (childItem == null) return convertView;

        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.elist_group_child, parent, false);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.img_1 = (ImageView) convertView.findViewById(R.id.img_first);
            holder.img_2 = (ImageView) convertView.findViewById(R.id.img_second);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        List<RecordDocumentsBean> recordDocuments = childItem.getRecordDocuments();
        if (recordDocuments != null && recordDocuments.size() > 0) {
            isDisplayImages(holder, recordDocuments);
        } else {
            holder.img_1.setVisibility(View.INVISIBLE);
            holder.img_2.setVisibility(View.INVISIBLE);
        }

        holder.tv_name.setText(childItem.getDepartment().getDepartmentName());
        holder.tv_date.setText(DateUtils.getFormatDate(childItem.getRecordDate()).split("T")[0]);
        //判断是否是入院记录，院内治疗相关记录，出院记录
        if (childItem.getCategory().equals("临床诊疗记录")) {
            if (childItem.getRecord().getRecordType().equals("入院记录") ||
                    childItem.getRecord().getRecordType().equals("院内治疗相关记录") ||
                    childItem.getRecord().getRecordType().equals("出院记录")) {
                holder.tv_name.setText(childItem.getHospital());
            }
        }
        return convertView;
    }

    /**
     * 1、过滤标记已经删除的数据,留下false
     * 2、计算size
     */
    private void isDisplayImages(final ChildHolder holder, List<RecordDocumentsBean> recordDocuments) {
        Observable.from(recordDocuments)
                .filter(recordDocumentsBean -> !recordDocumentsBean.isIsDeleted())
                .count()
                .subscribe(integer -> {
                    holder.img_1.setVisibility(integer != 0 ? View.VISIBLE : View.INVISIBLE);
                    holder.img_2.setVisibility(integer > 1 ? View.VISIBLE : View.INVISIBLE);
                });
    }

    private static class ChildHolder {
        TextView tv_name;
        TextView tv_date;
        ImageView img_1;
        ImageView img_2;
    }
}
