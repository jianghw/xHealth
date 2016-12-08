package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.ReferralMessageAlertDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.date.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jianghw on 2016/10/24.
 * <p/>
 * Describe: 复诊提醒
 */

public class VisitRecordAdapter extends CommonAdapter<ReferralMessageAlertDisplayBean> {

    private final ItemClickBack itemClickBack;
    public VisitRecordAdapter(Context context, List<ReferralMessageAlertDisplayBean> list, ItemClickBack itemClickBack) {
        super(context, list);
        this.itemClickBack=itemClickBack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.listview_item_visit_record, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position), position,itemClickBack);
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.tv_delete)
        TextView mTvDelete;
        @Bind(R.id.tv_time)
        TextView mTvTime;
        @Bind(R.id.tv_event)
        TextView mTvEvent;
        @Bind(R.id.tv_found_time)
        TextView mTvFoundTime;
        private int referralMessageAlertId;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(ReferralMessageAlertDisplayBean displayBean, int position, ItemClickBack itemClickBack) {
            if (displayBean == null) return;
            referralMessageAlertId = displayBean.getReferralMessageAlertId();
            mTvTime.setText(DateUtils.GetDateText(displayBean.getReminderTime(),"yyyy-MM-dd HH:mm"));
            mTvEvent.setText(displayBean.getReminderContent());
            mTvFoundTime.setText(DateUtils.GetDateText(displayBean.getCreatTime(),"yyyy-MM-dd HH:mm"));
        }
        @OnClick({R.id.tv_delete})
        public void itemDelete(){
            itemClickBack.onItemDeleteClick(referralMessageAlertId);
        }
    }

    public interface ItemClickBack {
        void onItemDeleteClick(int referralMessageAlertId);
    }
}
