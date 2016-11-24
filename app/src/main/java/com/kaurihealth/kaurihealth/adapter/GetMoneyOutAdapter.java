package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kaurihealth.datalib.request_bean.bean.CreditTransactionDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.date.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/7/20.
 * 介绍：取现
 */
public class GetMoneyOutAdapter extends CommonAdapter<CreditTransactionDisplayBean> {
    @Bind(R.id.tvMoney)
    TextView tvMoney;
    @Bind(R.id.lv_content)
    ListView lvContent;
    @Bind(R.id.rfreshLay)
    SwipeRefreshLayout rfreshLay;
    private ViewHolder viewHolder;

    public GetMoneyOutAdapter(Context context, List<CreditTransactionDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.get_money_out_detail, null);
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
            try {
                tvDate.setText(DateUtils.GetDateText(iteam.date, "yyyy.MM.dd HH:mm"));
            } catch (NullPointerException ex) {
                tvDate.setText("");
            }
            tvMoney.setText(String.format("%.2f元", iteam.amount));
        }
    }
}
