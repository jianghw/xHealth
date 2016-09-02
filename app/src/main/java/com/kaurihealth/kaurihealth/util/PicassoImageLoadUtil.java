package com.kaurihealth.kaurihealth.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.kaurihealth.kaurihealth.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by 张磊 on 2016/5/19.
 * 介绍：
 */
public class PicassoImageLoadUtil {

    public static void loadUrlToImageView(final String url, final ImageView imageView, final Context context) {
        if (url == null || url.trim().equals("")) {
            return;
        }
        int width = imageView.getWidth();
        if (width == 0) {
            imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Picasso.with(context).load(url).resize(imageView.getWidth(), imageView.getHeight()).placeholder(R.mipmap.photo_holder).error(R.mipmap.ic_patient_error).centerCrop().into(imageView);
                }
            });
        } else {
            Picasso.with(context).load(url).resize(imageView.getWidth(), imageView.getHeight()).placeholder(R.mipmap.photo_holder).error(R.mipmap.ic_patient_error).centerCrop().into(imageView);
        }
    }

    public static void loadUrlToImageView(final File file, final ImageView imageView, final Context context) {
        int width = imageView.getWidth();
        if (width == 0) {
            imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    imageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Picasso.with(context).load(file).resize(imageView.getWidth(), imageView.getHeight()).placeholder(R.mipmap.photo_holder).error(R.mipmap.ic_patient_error).centerCrop().into(imageView);
                }
            });
        } else {
            Picasso.with(context).load(file).resize(imageView.getWidth(), imageView.getHeight()).placeholder(R.mipmap.photo_holder).error(R.mipmap.ic_patient_error).centerCrop().into(imageView);
        }
    }

    public static void loadUrlToImageView(final String url, final ImageView imageView, Context context, ImagSizeMode.ImageSizeBean imageSizeBean) {
        if (!(TextUtils.isEmpty(url))) {
            Picasso.with(context).load(url + imageSizeBean.size).resize(imageSizeBean.width, imageSizeBean.width).placeholder(R.mipmap.photo_holder).error(R.mipmap.ic_patient_error).centerCrop().into(imageView);
        }
    }
}
