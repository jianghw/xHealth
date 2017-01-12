package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.SicknessesBean;
import com.kaurihealth.kaurihealth.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 介绍：搜索疾病
 */
public class SearchAllSicknessAdapter extends CommonAdapter<SicknessesBean> {

    private final List<SicknessesBean> sicknesseList;
    private OnSearchBoxItem mBoxListener;

    public SearchAllSicknessAdapter(Context context, List<SicknessesBean> list, List<SicknessesBean> sicknesseList) {
        super(context, list);
        this.sicknesseList = sicknesseList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.elist_group_sickness_child, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(list.get(position));
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.tv_sicknessName)
        TextView tv_name;
        @Bind(R.id.cb_choice)
        CheckBox cb_choice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(SicknessesBean childItem) {
            tv_name.setText(childItem.getSicknessName());
            cb_choice.setChecked(false);
            for (SicknessesBean bean : sicknesseList) {
                if (bean.getSicknessName().equals(childItem.getSicknessName())) {
                    cb_choice.setChecked(true);
                    break;
                }
            }
            cb_choice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBoxListener != null) mBoxListener.onSearchBOxItem(childItem);
                }
            });
        }
    }

    public void setSearchBoxListener(OnSearchBoxItem listener) {
        this.mBoxListener = listener;
    }

    public interface OnSearchBoxItem {
        void onSearchBOxItem(SicknessesBean childItem);
    }
}
