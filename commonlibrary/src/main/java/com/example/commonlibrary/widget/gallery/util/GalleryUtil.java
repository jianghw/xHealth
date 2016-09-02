package com.example.commonlibrary.widget.gallery.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.commonlibrary.widget.gallery.activity.GalleryActivity;

/**
 * Created by 张磊 on 2016/5/19.
 * 介绍：
 */
public class GalleryUtil {
    @Deprecated
    public static void toGallery(Activity srcActivity, GetUrlsInterface getUrlsInterface) {
        Intent intent = new Intent(srcActivity, GalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GalleryActivity.Data, getUrlsInterface.getUrls());
        intent.putExtras(bundle);
        srcActivity.startActivity(intent);
    }

    public static void toGallery(Activity srcActivity, GetUrlsInterface getUrlsInterface, int position) {
        Intent intent = new Intent(srcActivity, GalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GalleryActivity.Data, getUrlsInterface.getUrls());
        bundle.putInt(GalleryActivity.PositionKey, position);
        intent.putExtras(bundle);
        srcActivity.startActivity(intent);
    }
}
