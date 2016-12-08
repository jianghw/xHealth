package com.kaurihealth.utilslib.image;

import com.kaurihealth.utilslib.CheckUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by jianghw on 2016/9/22.
 * <p>
 * 描述：Picasso相关实现 统一实现
 */
public class PicassoImp implements ImageLoaderable {
    @Override
    public void setImageWrapper(ImageWrapper imageWrapper) {
        CheckUtils.checkNullArgument(imageWrapper, "You are the monkey sent to funny,imageWrapper can not null");
        Picasso picasso = Picasso.with(imageWrapper.getContext());
        RequestCreator requestCreator = (imageWrapper.getUri() != null) ?
                picasso.load(imageWrapper.getUri()) : picasso.load(imageWrapper.getResourceId());
        requestCreator = (imageWrapper.getErrorDrawable() != null) ?
                requestCreator.error(imageWrapper.getErrorDrawable()) : requestCreator;
        requestCreator = (imageWrapper.getPlaceHolderDrawable() != null) ?
                requestCreator.placeholder(imageWrapper.getPlaceHolderDrawable()) : requestCreator;
        requestCreator = (imageWrapper.isResize()) ?
                requestCreator.resize(imageWrapper.getTargetWidth(), imageWrapper.getTargetWidth()) : requestCreator;
        requestCreator = (imageWrapper.isFitCenter()) ? requestCreator.fit() : requestCreator;
        requestCreator = (imageWrapper.isCenterCrop()) ? requestCreator.centerCrop() : requestCreator;
        if (imageWrapper.getListener() == null) {
            requestCreator.into(imageWrapper.getImageView());
        } else {
            requestCreator.into(imageWrapper.getImageView(), new PicassoCallback(imageWrapper.getListener()));
        }
    }

    /**
     * 图片加载结果的回调，为Picasso的Callback设置自己的Listener进行代理
     */
    class PicassoCallback implements Callback {

        private final ImageWrapper.ImageListener imageListener;

        public PicassoCallback(ImageWrapper.ImageListener listener) {
            imageListener = listener;
        }

        @Override
        public void onSuccess() {
            imageListener.onComplete();
        }

        @Override
        public void onError() {
            imageListener.onError();
        }
    }
}
