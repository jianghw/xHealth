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
 * <p>
 * 描述：编辑、删除adapter
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
        return convertView;
    }

    @Override
    public void fillValues(int i, View view) {
        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(i));
        LongTermMonitoringDisplayBean bean = mList.get(i);
        view.findViewById(R.id.tv_compile).setOnClickListener(v -> {
            swipeLayout.close();
            EventBus.getDefault().postSticky(new GraphDataFgtDeleteEvent(Global.Environment.COMPILE, bean));
        });
        view.findViewById(R.id.tv_delete).setOnClickListener(v -> {
            swipeLayout.close();
            EventBus.getDefault().postSticky(new GraphDataFgtDeleteEvent(Global.Environment.DELETE, bean));
        });
        TextView mData = (TextView) view.findViewById(R.id.tv_data);
        TextView mDate = (TextView) view.findViewById(R.id.tv_date);

        String unit = bean.getUnit();
        if (bean.getMeasurement().contains("--")) {
            String[] strings = bean.getMeasurement().split("--");
            mData.setText(strings[0] + "--" + strings[1] + unit);
        } else {
            mData.setText(bean.getMeasurement() + unit);
        }
        String date = DateUtils.getFormatDate(bean.getDate());
        mDate.setText(date);
    }
}
