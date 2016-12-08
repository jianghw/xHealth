package com.kaurihealth.utilslib.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by jianghw on 2016/12/2.
 */

public abstract class NineGridImageViewAdapter<T> {

    protected abstract void onDisplayImage(Context context, ImageView imageView, T t);

    protected ImageView generateImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }
}
