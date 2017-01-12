package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.LoadAllSicknessItem;
import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.kaurihealth.R;

import java.util.List;

/**
 * 描述：疾病选择
 */
public class LoadAllSicknessAdapter extends BaseAExpandableListAdapter<LoadAllSicknessItem> {

    private final List<SicknessesBean> sicknesseList;
    private OnChildBoxListener mChildBoxListener;

    public LoadAllSicknessAdapter(Context context, List<LoadAllSicknessItem> data, List<SicknessesBean> sicknesseList) {
        super(context, data);
        this.sicknesseList = sicknesseList;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return data.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).getChildList().get(childPosition);
    }

    /**
     * 父布局
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder holder;
        LoadAllSicknessItem beanItem = data.get(groupPosition);
        if (convertView == null) {
            holder = new GroupHolder();
            convertView = inflater.inflate(R.layout.elist_group_sickness_iteam, parent, false);
            holder.tv_departmentName = (TextView) convertView.findViewById(R.id.tv_departmentName);
            holder.img_More = (ImageView) convertView.findViewById(R.id.img_more);
            convertView.setTag(holder);
        } else {
            holder = (GroupHolder) convertView.getTag();
        }
        holder.tv_departmentName.setText(beanItem.getTitle());
        holder.tv_departmentName.setTextColor(isExpanded ?
                mContext.getResources().getColor(R.color.color_green) : mContext.getResources().getColor(R.color.color_tab_black));

        holder.img_More.setImageResource(isExpanded ? R.drawable.more_icon_down : R.drawable.more_icon);

        return convertView;
    }

    private static class GroupHolder {

        TextView tv_departmentName;
        ImageView img_More;
    }

    /**
     * 子布局
     */
    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        SicknessesBean childItem = (SicknessesBean) getChild(groupPosition, childPosition);
        if (childItem == null) return convertView;

        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.elist_group_sickness_child, parent, false);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_sicknessName);
            holder.cb_choice = (CheckBox) convertView.findViewById(R.id.cb_choice);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }

        holder.tv_name.setText(childItem.getSicknessName());
        holder.cb_choice.setChecked(false);
        for (SicknessesBean bean : sicknesseList) {
            if (bean.getSicknessName().equals(childItem.getSicknessName())) {
                holder.cb_choice.setChecked(true);
                break;
            }
        }

        holder.cb_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChildBoxListener != null)
                    mChildBoxListener.onChildBoxListener(groupPosition, childPosition);
            }
        });
        return convertView;
    }

    public void setOnChildBoxListener(OnChildBoxListener listener) {
        this.mChildBoxListener = listener;
    }

    private static class ChildHolder {
        TextView tv_name;
        CheckBox cb_choice;
    }

    public interface OnChildBoxListener {
        void onChildBoxListener(int groupPosition, int childPosition);
    }
}
