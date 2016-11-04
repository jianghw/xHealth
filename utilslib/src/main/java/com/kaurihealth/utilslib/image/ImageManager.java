package com.kaurihealth.utilslib.image;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by jianghw on 2016/9/22.
 * <p>
 * 描述：暴露给客户端的图片管理类，用于子调用
 * 依托于,单例
 *
 * @see ImageHelper
 */
public class ImageManager {

    private ImageHelper imageHelper;

    public static ImageManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        static final ImageManager INSTANCE = new ImageManager();
    }

    /**
     * application 初始化，设置信息
     *
     * @param imageHelper
     */
    public void initializesGlobal(ImageHelper imageHelper) {
        this.imageHelper = imageHelper;
    }

    public static ImageManager with(Context context) {
        return getInstance();
    }

    /**
     * 图片地址
     *
     * @param uri
     * @return
     */
    public ImageHelper load(Uri uri) {
        if (imageHelper == null) {
            imageHelper = new ImageHelper(this, uri, 0);
        } else {
            imageHelper.getWrapper().setImageManager(this);
            imageHelper.getWrapper().setUri(uri);
        }
        return imageHelper;
    }

    public ImageHelper load(String path) {
        if (TextUtils.isEmpty(path)) return new ImageHelper(this, null, 0);
        return this.load(Uri.parse(path));
    }

    public ImageHelper load(File file) {
        if (file == null) return new ImageHelper(this, null, 0);
        return this.load(Uri.fromFile(file));
    }

    public ImageHelper load(int resId) {
        if (imageHelper == null) {
            imageHelper = new ImageHelper(this, null, resId);
        } else {
            imageHelper.getWrapper().setImageManager(this);
            imageHelper.getWrapper().setResourceId(resId);
        }
        return imageHelper;
    }

}
