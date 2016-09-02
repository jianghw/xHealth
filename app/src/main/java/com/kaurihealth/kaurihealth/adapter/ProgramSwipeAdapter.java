package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.kaurihealth.datalib.response_bean.LongTermMonitoringDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.eventbus.GraphDataFgtDeleteEvent;
import com.kaurihealth.utilslib.constant.Global;
import com.kaurihealth.utilslib.date.DateUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by jianghw on 2016/9/2.
 * <p/>
 * 描述：
 */
public class ProgramSwipeAdapter extends BaseSwipeAdapter {


    private final Context mContext;
    private final List<LongTermMonitoringDisplayBean> mList;

    public ProgramSwipeAdapter(Context context, List<LongTermMonitoringDisplayBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.swipe;
    }

    @Override
    public View generateView(final int i, ViewGroup viewGroup) {
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_swipe_layout, null);
        SwipeLayout swipeLayout = (SwipeLayout) convertView.findViewById(getSwipeLayoutResourceId(i));
        final LongTermMonitoringDisplayBean bean = mList.get(i);
        convertView.findViewById(R.id.tv_compile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new GraphDataFgtDeleteEvent(Global.Environment.COMPILE, bean));
            }
        });
        convertView.findViewById(R.id.tv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new GraphDataFgtDeleteEvent(Global.Environment.DELETE, bean));
            }
        });

        return convertView;
    }

    @Override
    public void fillValues(int i, View view) {
        TextView mData = (TextView) view.findViewById(R.id.tv_data);
        TextView mDate = (TextView) view.findViewById(R.id.tv_date);
        LongTermMonitoringDisplayBean bean = mList.get(i);
        String unit = bean.getUnit();
        mData.setText(bean.getMeasurement() + unit);
        String date = DateUtils.getFormatDate(bean.getDate());
        mDate.setText(date);
    }
}
