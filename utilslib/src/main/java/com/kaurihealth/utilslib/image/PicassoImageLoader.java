package com.kaurihealth.utilslib.image;

import android.content.Context;
import android.widget.ImageView;

import com.kaurihealth.utilslib.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

/**
 * Author: Alexey Nevinsky
 */
public class PicassoImageLoader implements MediaLoader {
    private String url;

    public PicassoImageLoader(String url) {
        this.url = url;
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final SuccessCallback callback) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder_image)
                .skipMemoryCache()
                .into(imageView, new ImageCallback(callback));
    }

    @Override
    public void loadThumbnail(Context context, ImageView thumbnailView, SuccessCallback callback) {
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder_image)
                .skipMemoryCache()
                .into(thumbnailView, new ImageCallback(callback));
    }

    private static class ImageCallback implements Callback {

        private final SuccessCallback callback;

        public ImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess() {
            callback.onSuccess();
        }

        @Override
        public void onError() {

        }
    }
}
