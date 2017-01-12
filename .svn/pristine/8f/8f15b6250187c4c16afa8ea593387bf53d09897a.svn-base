package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.TotalLabTestCountsBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jianghw on 2016/10/24.
 * <p/>
 * Describe: 实验室检查统计
 */

public class RecordLabAdapter extends CommonAdapter<TotalLabTestCountsBean> {

    private ItemClickBack itemClickBack;

    public RecordLabAdapter(Context context, List<TotalLabTestCountsBean> list) {
        super(context, list);
    }

    public RecordLabAdapter(Context context, List<TotalLabTestCountsBean> list, ItemClickBack itemClickBack) {
        super(context, list);
        this.itemClickBack = itemClickBack;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.listview_item_record_accessory, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(list.get(position));
        return convertView;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return false;
    }

    class ViewHolder {

        @Bind(R.id.tv_keyName)
        TextView mTvKeyName;
        @Bind(R.id.tv_count)
        TextView mTvCount;
        @Bind(R.id.img_count)
        ImageView mImgCount;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(TotalLabTestCountsBean displayBean) {
            if (displayBean == null) return;
            mTvKeyName.setText(CheckUtils.checkTextContent(displayBean.getKeyName()));
            int mCount = displayBean.getCount();
            mTvCount.setText(CheckUtils.checkTextContent(context.getResources().getString(R.string.accessory_count,mCount)));
            mImgCount.setVisibility(list.size() > 0 ? View.VISIBLE : View.GONE);
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
