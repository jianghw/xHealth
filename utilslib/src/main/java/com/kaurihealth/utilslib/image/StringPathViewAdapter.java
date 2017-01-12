package com.kaurihealth.utilslib.image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.R;
import com.kaurihealth.utilslib.widget.CircleImageView;

import java.io.File;
import java.util.List;

public class StringPathViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<String> mList;

    public StringPathViewAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.path_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        viewHolder.setInfo(mList.get(position));
        return convertView;
    }

    class ViewHolder {
        CircleImageView mCircleImageView;

        ViewHolder(View view) {
            mCircleImageView = (CircleImageView) view.findViewById(R.id.cv_img);
        }

        public void setInfo(String url) {
            if (CheckUtils.checkUrlNotNull(url)) {
                if (url.contains("http")) {
                    ImageUrlUtils.picassoBySmallUrlCircle(mContext, url, mCircleImageView);
                } else {
                    ImageUrlUtils.picassoBySmallFileCircle(mContext, new File(url), mCircleImageView);
                }
            } else {
                ImageUrlUtils.picassoBySmallUrlCircle(mContext, mCircleImageView);
            }
        }
    }
}
