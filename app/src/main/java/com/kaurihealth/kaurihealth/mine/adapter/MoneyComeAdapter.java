package com.kaurihealth.kaurihealth.mine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.kaurihealth.utilslib.date.DateUtils;
import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/7/20.
 * 介绍：收取明细
 */
public class MoneyComeAdapter extends CommonAdapter<CreditTransactionDisplayBean> {

    private ViewHolder viewHolder;

    public MoneyComeAdapter(Context context, List<CreditTransactionDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.come_money_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.setData(list.get(position));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tvAge)
        TextView tvTitle;
        @Bind(R.id.tvDate)
        TextView tvDate;
        @Bind(R.id.tvMoney)
        TextView tvMoney;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(CreditTransactionDisplayBean iteam) {
            tvMoney.setText(String.format("+%.2f元", iteam.amount));
            try {
                tvTitle.setText(iteam.order.patientRequest.requestType);
            } catch (NullPointerException ex) {
                Bugtags.sendException(ex);
                tvTitle.setText("");
            }
            try {
                tvDate.setText(DateUtils.GetDateText(iteam.order.patientRequest.endDate, "yyyy.MM.dd HH:mm"));
            } catch (NullPointerException ex) {
                Bugtags.sendException(ex);
                tvDate.setText("");
            }
        }
    }
}
