package com.youyou.zllibrary.netutil;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.youyou.zllibrary.R;
import com.youyou.zllibrary.util.StringUtils;
/**
 * 版权:    张磊  2016/1/18.
 * 作者:    张磊
 * 描述：
 * 修订日期:
 */
@Deprecated
public class ImageLoadUtil {

    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    public static void displayImg(String url, ImageView view, Context context) {
        if(StringUtils.isEmpty(url)){
            return;
        }
        if (imageLoader == null) {
            requestQueue = Volley.newRequestQueue(context);
            imageLoader = new ImageLoader(requestQueue, new BitmapCache());
        }
        ImageLoader.ImageListener imageListener= ImageLoader.getImageListener(view, R.mipmap.loading,R.mipmap.ic_patient_error);
        imageLoader.get(url, imageListener);
    }
}
