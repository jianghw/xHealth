package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.ResidualAccountBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.adapter.CommonAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/3/22.
 * 备注：
 */
public class ResidualAccountAdapter extends CommonAdapter<ResidualAccountBean> {


    public ResidualAccountAdapter(Context context, List<ResidualAccountBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.residuals_iteam, null);
        }
        ResidualAccountBean item = list.get(position);
        ViewHolder ViewHolder = new ViewHolder(convertView);
        ViewHolder.setData(item);
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.tv_type)
        TextView tvType;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_amount)
        TextView tvAmount;
        @Bind(R.id.tv_currentTotal)
        TextView tvCurrentTotal;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(ResidualAccountBean item) {
            tvType.setText(item.type);
            tvTime.setText(item.effectedDate);
            tvAmount.setText(String.format("余额：%.2f", item.amount));
            tvCurrentTotal.setText(String.format("%.2f", item.currentTotal));
        }
    }
}
