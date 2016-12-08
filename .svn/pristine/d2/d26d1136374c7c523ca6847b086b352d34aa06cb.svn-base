package com.kaurihealth.utilslib.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by jianghw on 2016/9/22.
 * <p>
 * 描述：辅助ImageManager来配置图片加载,可用于全局设置信息
 *
 * @see ImageManager
 * @see ImageWrapper
 */
public class ImageHelper {

    private final ImageWrapper imageWrapper;
    private Context context = null;

    public ImageHelper(Context context) {
        imageWrapper = new ImageWrapper();
        imageWrapper.setContext(context);
        this.context = context.getApplicationContext();
    }

    ImageHelper(ImageManager imageManager, Uri uri, int resId) {
        imageWrapper = new ImageWrapper();
        imageWrapper.setImageManager(imageManager);
        imageWrapper.setContext(context);
        imageWrapper.setUri(uri);
        imageWrapper.setResourceId(resId);
    }

    /**
     * 错误图片
     *
     * @param errorDrawable
     * @return
     */
    public ImageHelper error(Drawable errorDrawable) {
        if (errorDrawable == null) {
            throw new IllegalArgumentException("placeHolderDrawable can't be null");
        }
        imageWrapper.setErrorDrawable(errorDrawable);
        return this;
    }

    public ImageHelper error(int errorResId) {
        imageWrapper.setErrorResId(errorResId);
        if (errorResId == 0) {
            throw new IllegalArgumentException("errorResId can't be 0");
        }
        imageWrapper.setErrorDrawable(imageWrapper.getContext().getResources().getDrawable(errorResId));
        return this;
    }

    /**
     * 没有加载图片时显示的默认图
     *
     * @param placeHolderDrawable
     * @return
     */
    public ImageHelper placeholder(Drawable placeHolderDrawable) {
        if (placeHolderDrawable == null) {
            throw new IllegalArgumentException("placeHolderDrawable can't be null");
        }
        imageWrapper.setPlaceHolderDrawable(placeHolderDrawable);
        return this;
    }

    public ImageHelper placeholder(int placeHolderResId) {
        imageWrapper.setPlaceHolderResId(placeHolderResId);
        if (placeHolderResId == 0) {
            throw new IllegalArgumentException("placeHolderResId can't be 0");
        }
        imageWrapper.setPlaceHolderDrawable(imageWrapper.getContext().getResources().getDrawable(placeHolderResId));
        return this;
    }

    public ImageHelper centerCrop() {
        imageWrapper.setCenterCrop(true);
        return this;
    }

    public ImageHelper fit() {
        imageWrapper.setFitCenter(true);
        return this;
    }

    /**
     * 转换图片以适应布局大小并减少内存占用
     * 压缩到指定尺寸
     *
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public ImageHelper resize(int targetWidth, int targetHeight) {
        imageWrapper.setResize(true);
        imageWrapper.setTargetWidth(targetWidth);
        imageWrapper.setTargetHeight(targetHeight);
        return this;
    }

    public void into(ImageView target) {
        this.into(target, null);
    }

    /**
     * 调用第三方
     *
     * @param target
     * @param listener
     */
    public void into(ImageView target, ImageWrapper.ImageListener listener) {
        imageWrapper.setImageView(target);
        imageWrapper.setListener(listener);
        //非空传值断开操作
        if (imageWrapper.getUri() == null && imageWrapper.getResourceId() == 0) return;

        PicassoImp iLoader = new PicassoImp();
        iLoader.setImageWrapper(imageWrapper);
    }

    public ImageWrapper getWrapper() {
        return imageWrapper;
    }
}
