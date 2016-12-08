package com.kaurihealth.utilslib.image;

/**
 * Created by 张磊 on 2016/6/13.
 * 介绍：
 */
public class ImagSizeMode {
    private ImageSizeBean curImageSizeBean;
    public static ImageSizeBean[] imageSizeBeens =
            {new ImageSizeBean(30, "@30w_30h.jpg"),
                    new ImageSizeBean(45, "@45w_45h.jpg"),
                    new ImageSizeBean(90, "@90w_90h.jpg"),
                    new ImageSizeBean(150, "@150w_150h.jpg"),
                    new ImageSizeBean(200, "@200w_200h.jpg")};

    public ImagSizeMode(int width) {
        set(width);
    }

    public int getWidth() {
        return curImageSizeBean.width;
    }

    public String getMode() {
        return curImageSizeBean.size;
    }

    private void set(int width) {
        if (width <= (imageSizeBeens[0].width + imageSizeBeens[1].width) / 2) {
            this.curImageSizeBean = imageSizeBeens[0];
            return;
        } else if (width <= (imageSizeBeens[1].width + imageSizeBeens[2].width) / 2) {
            this.curImageSizeBean = imageSizeBeens[1];
            return;
        } else if (width <= (imageSizeBeens[2].width + imageSizeBeens[3].width) / 2) {
            this.curImageSizeBean = imageSizeBeens[2];
            return;
        } else if (width <= (imageSizeBeens[3].width + imageSizeBeens[4].width) / 2) {
            this.curImageSizeBean = imageSizeBeens[3];
            return;
        } else {
            this.curImageSizeBean = imageSizeBeens[4];
            return;
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
}
