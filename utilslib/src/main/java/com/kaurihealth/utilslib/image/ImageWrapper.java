package com.kaurihealth.utilslib.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Created by jianghw on 2016/9/22.
 * <p>
 * 描述：图片包装器,建造者
 */
public class ImageWrapper {
    private  int targetWidth;
    private   int targetHeight;
    private  int errorResId;
    private  int placeHolderResId;
    private  boolean isResize;
    private  boolean isFitCenter;
    private  boolean isCenterCrop;
    private  Drawable errorDrawable;
    private  Drawable placeHolderDrawable;

    private ImageListener listener;

    private Context mContext;
    private  int resourceId;
    private ImageManager mImageManager;
    private Uri mUri;
    private ImageView imageView;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageListener getListener() {
        return listener;
    }

    public void setListener(ImageListener listener) {
        this.listener = listener;
    }

    public Context getContext() {
        return mContext;
    }

    public int getResourceId() {
        return resourceId;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public int getErrorResId() {
        return errorResId;
    }

    public int getPlaceHolderResId() {
        return placeHolderResId;
    }

    public boolean isResize() {
        return isResize;
    }

    public boolean isFitCenter() {
        return isFitCenter;
    }

    public boolean isCenterCrop() {
        return isCenterCrop;
    }

    public Drawable getErrorDrawable() {
        return errorDrawable;
    }

    public Drawable getPlaceHolderDrawable() {
        return placeHolderDrawable;
    }

    public ImageManager getImageManager() {
        return mImageManager;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setContext(Context context) {
        mContext=context;
    }

    public void setImageManager(ImageManager imageManager) {
        mImageManager=imageManager;
    }

    public void setUri(Uri uri) {
        mUri=uri;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
    }

    public void setTargetHeight(int targetHeight) {
        this.targetHeight = targetHeight;
    }

    public void setErrorResId(int errorResId) {
        this.errorResId = errorResId;
    }

    public void setPlaceHolderResId(int placeHolderResId) {
        this.placeHolderResId = placeHolderResId;
    }

    public void setResize(boolean resize) {
        isResize = resize;
    }

    public void setFitCenter(boolean fitCenter) {
        isFitCenter = fitCenter;
    }

    public void setCenterCrop(boolean centerCrop) {
        isCenterCrop = centerCrop;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        this.errorDrawable = errorDrawable;
    }

    public void setPlaceHolderDrawable(Drawable placeHolderDrawable) {
        this.placeHolderDrawable = placeHolderDrawable;
    }

    public static class Builder {
        private int targetWidth;
        private int targetHeight;
        private int errorResId;
        private int placeHolderResId;
        private boolean isResize;
        private boolean isFitCenter;
        private boolean isCenterCrop;
        private Drawable errorDrawable;
        private Drawable placeHolderDrawable;

        public ImageWrapper build(){
            ImageWrapper imageWrapper = new ImageWrapper();
            imageWrapper.targetWidth = targetWidth;
            imageWrapper.targetHeight = targetHeight;
            imageWrapper.errorResId = errorResId;
            imageWrapper.placeHolderResId = placeHolderResId;
            imageWrapper.isResize = isResize;
            imageWrapper.isFitCenter = isFitCenter;
            imageWrapper.isCenterCrop = isCenterCrop;
            imageWrapper.errorDrawable = errorDrawable;
            imageWrapper.placeHolderDrawable = placeHolderDrawable;
            return imageWrapper;
        }

        public Builder targetWidth(int targetWidth) {
            this.targetWidth = targetWidth;
            return this;
        }

        public Builder targetHeight(int targetHeight) {
            this.targetHeight = targetHeight;
            return this;
        }

        public Builder errorResId(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder placeHolderResId(int placeHolderResId) {
            this.placeHolderResId = placeHolderResId;
            return this;
        }

        public Builder isResize(boolean isResize) {
            this.isResize = isResize;
            return this;
        }

        public Builder isFitCenter(boolean isFitCenter) {
            this.isFitCenter = isFitCenter;
            return this;
        }

        public Builder isCenterCrop(boolean isCenterCrop) {
            this.isCenterCrop = isCenterCrop;
            return this;
        }

        public Builder errorDrawable(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder placeHolderDrawable(Drawable placeHolderDrawable) {
            this.placeHolderDrawable = placeHolderDrawable;
            return this;
        }
    }

    /**
     * Created by jianghw on 2016/9/22.
     * <p>
     * 描述：
     */
    public interface ImageListener {
        /**
         * 图片加载成功的回调
         */
        void onComplete();

        /**
         * 图片加载失败的回调
         */
        void onError();
    }
}
