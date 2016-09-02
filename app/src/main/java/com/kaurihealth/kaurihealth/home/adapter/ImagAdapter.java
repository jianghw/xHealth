package com.kaurihealth.kaurihealth.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.datalib.request_bean.bean.RecordDocumentDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.ImagSizeMode;
import com.squareup.picasso.Picasso;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by 张磊 on 2016/6/24.
 * 介绍：
 */
public class ImagAdapter extends CommonAdapter<Object> {
    public ImagAdapter(Context context, List<Object> list) {
        super(context, list);
    }

    ImagSizeMode imagSizeMode;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = (CircleImageView) inflater.inflate(R.layout.uriimageview, null);
        }
        final CircleImageView content = (CircleImageView) convertView.findViewById(R.id.iv);
        final int width = content.getWidth();
        final Object iteam = list.get(position);
        if (width <= 0) {
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (imagSizeMode == null) {
                        imagSizeMode = new ImagSizeMode(content.getWidth());
                    }
                    displayImag(iteam, content);
                }
            });
        } else {
            displayImag(iteam, content);
        }
        return convertView;
    }

    private void displayImag(Object object, ImageView imageView) {
        if (object instanceof String) {
            Picasso.with(context).load(new File((String) object)).
                    placeholder(R.mipmap.photo_holder)
                    .resize(imageView.getWidth(), imageView.getHeight()).
                    centerCrop().into(imageView);
        } else if (object instanceof RecordDocumentDisplayBean) {
            Picasso.with(context).load(((RecordDocumentDisplayBean) object).documentUrl + imagSizeMode.getMode()).resize(imagSizeMode.getWidth(), imagSizeMode.getWidth()).centerCrop().into(imageView);
        }
    }
}
