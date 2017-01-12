package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.AllSicknessDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 介绍：左边选择栏
 */
public class LoadAllSicknessLeftAdapter extends CommonAdapter<AllSicknessDisplayBean> {

    private int mCurPositon;

    public LoadAllSicknessLeftAdapter(Context context, List<AllSicknessDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_sickness_left, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(position);
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    public void setItemSelected(int position) {
        this.mCurPositon = position;
    }

    class ViewHolder {
        @Bind(R.id.tv_departmentName)
        TextView mTvDepartmentName;
        @Bind(R.id.tv_line)
        TextView mTvLine;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            mTvDepartmentName.setText(CheckUtils.checkTextContent(list.get(position) != null ? list.get(position).getDepartmentName() : null));
            mTvDepartmentName.setEnabled(mCurPositon != position);
            mTvLine.setEnabled(mCurPositon != position);
        }
    }
}
