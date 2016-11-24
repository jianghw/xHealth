package com.kaurihealth.utilslib.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.kaurihealth.utilslib.CheckUtils;
import com.kaurihealth.utilslib.R;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by jianghw on 2016/10/27.
 * <p/>
 * Describe:
 */

public class ImageUrlUtils {

    public static ImageSizeBean[] imageSizeBeens =
            {
                    new ImageSizeBean(30, "@30w_30h.jpg"),
                    new ImageSizeBean(45, "@45w_45h.jpg"),
                    new ImageSizeBean(90, "@90w_90h.jpg"),
                    new ImageSizeBean(150, "@150w_150h.jpg"),
                    new ImageSizeBean(200, "@200w_200h.jpg")
            };

    /**
     * 默认图片加载url小图
     */
    private static String smallUrl_Three(String url) {
        return url + imageSizeBeens[3].size;
    }

    private static String smallUrl_Four(String url) {
        return url + imageSizeBeens[4].size;
    }

    public static String SmallUrlByPosition(String url, int positon) {
        return url + imageSizeBeens[positon].size;
    }

    public static String SmallUrlByWith(String url, int width) {
        return url + getImageSize(width);
    }

    private static String getImageSize(int width) {
        if (width <= (imageSizeBeens[0].width + imageSizeBeens[1].width) / 2) {
            return imageSizeBeens[0].size;
        } else if (width <= (imageSizeBeens[1].width + imageSizeBeens[2].width) / 2) {
            return imageSizeBeens[1].size;
        } else if (width <= (imageSizeBeens[2].width + imageSizeBeens[3].width) / 2) {
            return imageSizeBeens[2].size;
        } else if (width <= (imageSizeBeens[3].width + imageSizeBeens[4].width) / 2) {
            return imageSizeBeens[3].size;
        } else {
            return imageSizeBeens[4].size;
        }
    }

    public static class ImageSizeBean {
        public int width;
        public String size;

        public ImageSizeBean(int width, String size) {
            this.width = width;
            this.size = size;
        }
    }

    /**
     * 临床 长方形
     */
    public static void picassoBySmallUrlReference(Context context, String url, ImageView target) {
        if (CheckUtils.checkUrlNotNull(url)) {
            Picasso.with(context)
                    .load(smallUrl_Four(url))
                    .placeholder(R.mipmap.ic_clinical_rectangle)
                    .error(R.mipmap.ic_clinical_rectangle)
                    .resizeDimen(R.dimen.margin_80, R.dimen.margin_60)
                    .centerCrop()
                    .into(target);
        }
    }

    /**
     * 圆头像  48dp
     */
    public static void picassoBySmallUrlCircle(Context context, String url, ImageView target) {
        if (CheckUtils.checkUrlNotNull(url)) {
            Picasso.with(context)
                    .load(smallUrl_Three(url))
                    .placeholder(R.mipmap.ic_circle_head_green)
                    .error(R.mipmap.ic_circle_error_gray)
                    .resizeDimen(R.dimen.margin_48, R.dimen.margin_48)
                    .centerCrop()
                    .into(target);
        }
    }

    public static void picassoBySmallUrlCircle(Context context, ImageView target) {
        Picasso.with(context)
                .load(R.mipmap.ic_circle_head_green)
                .resizeDimen(R.dimen.margin_48, R.dimen.margin_48)
                .centerCrop()
                .into(target);

    }

    /**
     * 60dp
     */
    public static void picassoByUrlCircle(Context context, String url, ImageView target) {
        if (CheckUtils.checkUrlNotNull(url)) {
            Picasso.with(context)
                    .load(smallUrl_Three(url))
                    .placeholder(R.mipmap.ic_circle_head_green)
                    .error(R.mipmap.ic_circle_error_gray)
                    .resizeDimen(R.dimen.margin_60, R.dimen.margin_60)
                    .centerCrop()
                    .into(target);
        }
    }

    public static void picassoBySmallFileCircle(Context context, File file, ImageView target) {
        if (file != null) {
            Picasso.with(context)
                    .load(Uri.fromFile(file))
                    .placeholder(R.mipmap.ic_circle_head_green)
                    .error(R.mipmap.ic_circle_error_gray)
                    .resizeDimen(R.dimen.margin_60, R.dimen.margin_60)
                    .centerCrop()
                    .into(target);
        }
    }
}
