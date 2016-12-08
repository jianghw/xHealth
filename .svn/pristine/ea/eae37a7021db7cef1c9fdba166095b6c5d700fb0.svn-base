package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.NotifyDisplayBean;
import com.kaurihealth.datalib.response_bean.UserNotifyDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.date.DateUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/10/24.
 * <p/>
 * Describe: 复诊提醒
 */

public class SystemMessageAdapter extends CommonAdapter<UserNotifyDisplayBean> {

    private final ItemClickBack itemClickBack;

    public SystemMessageAdapter(Context context, List<UserNotifyDisplayBean> list, ItemClickBack itemClickBack) {
        super(context, list);
        this.itemClickBack = itemClickBack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.listview_item_system_message, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position), position);
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {
        @Bind(R.id.tv_message_title)
        TextView mTvMessageTitle;
        @Bind(R.id.tv_event)
        TextView mTvEvent;
        @Bind(R.id.tv_message_time)
        TextView mTvMessageTime;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(UserNotifyDisplayBean displayBean, int position) {
            if (displayBean == null) return;
            NotifyDisplayBean notifyDisplayDto = displayBean.getNotifyDisplayDto();
            if (notifyDisplayDto != null) {
                mTvEvent.setText(notifyDisplayDto.getContent());
                mTvMessageTime.setText(DateUtils.GetDateText(notifyDisplayDto.getCreatedAt(),"yyyy-MM-dd HH:mm"));
                mTvMessageTitle.setText(notifyDisplayDto.getSubject());
            }
        }

//        @OnClick({R.id.tv_delete})
//        public void itemDelete() {
//            itemClickBack.onItemClick(1);
//        }
    }

    public interface ItemClickBack {
        void onItemClick(int referralMessageAlertId);
    }
}
