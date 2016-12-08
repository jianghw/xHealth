package com.kaurihealth.kaurihealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kaurihealth.datalib.response_bean.ContactUserDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.image.ImageUrlUtils;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 张磊 on 2016/4/18.
 * 介绍：
 */
public class HeaderFooterGridAdapter extends CommonAdapter<ContactUserDisplayBean> {

    public HeaderFooterGridAdapter(Context context, List<ContactUserDisplayBean> list) {
        super(context, list);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HeaderFooterGridAdapter.ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (HeaderFooterGridAdapter.ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.gv_item_group_conver, null);
            viewHolder = new HeaderFooterGridAdapter.ViewHolder(convertView);
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
        @Bind(R.id.cv_img)
        CircleImageView mCircleImageView;
        @Bind(R.id.tv_name)
        TextView mTvName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setInfo(ContactUserDisplayBean displayBean, int position) {
            if (displayBean == null) return;
            if (CheckUtils.checkUrlNotNull(displayBean.getAvatar())) {
                ImageUrlUtils.picassoBySmallUrlCircle(context, displayBean.getAvatar(), mCircleImageView);
            } else {
                ImageUrlUtils.picassoBySmallUrlCircle(context, mCircleImageView);
            }
            mTvName.setText(displayBean.getFullName());
        }

    }
}