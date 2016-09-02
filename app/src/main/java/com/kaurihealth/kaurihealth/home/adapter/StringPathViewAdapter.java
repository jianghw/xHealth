package com.kaurihealth.kaurihealth.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.commonlibrary.widget.CircleImageView;
import com.kaurihealth.kaurihealth.R;
import com.squareup.picasso.Picasso;
import com.youyou.zllibrary.widget.CommonAdapter;

import java.io.File;
import java.util.List;

/**
 * Created by 张磊 on 2016/4/18.
 * 介绍：
 */
public class StringPathViewAdapter extends CommonAdapter<String> {
    public StringPathViewAdapter(Context context, List<String> list) {
        super(context, list);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = (CircleImageView) inflater.inflate(R.layout.uriimageview, null);
        }
        final CircleImageView content = (CircleImageView) convertView.findViewById(R.id.iv);
        int width = content.getWidth();
        if (width <= 0) {
            content.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    content.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    Picasso.with(context).load(new File(list.get(position))).
                            placeholder(R.mipmap.photo_holder)
                            .resize(content.getWidth(), content.getHeight()).
                            centerCrop().into(content);
                }
            });
        } else {
            Picasso.with(context).load(new File(list.get(position))).
                    placeholder(R.mipmap.photo_holder).
                    resize(content.getWidth(), content.getHeight())
                    .centerCrop().into(content);
        }
        return convertView;
    }
}
