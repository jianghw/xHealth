package com.kaurihealth.utilslib.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;


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
        bundle.putSerializable(GalleryActivity.Data, getUrlsInterface.getUrls());   //"Data  键 key-value"
        bundle.putInt(GalleryActivity.PositionKey, position);       //"PositionKey 键  key - value"
        intent.putExtras(bundle);
        srcActivity.startActivity(intent);
    }

    public static void toGallery(Context context, GetUrlsInterface getUrlsInterface, int position) {
        Intent intent = new Intent(context, GalleryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(GalleryActivity.Data, getUrlsInterface.getUrls());   //"Data  键 key-value"
        bundle.putInt(GalleryActivity.PositionKey, position);       //"PositionKey 键  key - value"
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * Created by 张磊 on 2016/5/19.
     * 介绍：
     */
    public interface GetUrlsInterface {
        ArrayList<String> getUrls();
    }
}
