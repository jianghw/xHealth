package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by jianghw on 2016/8/22.
 * <p/>
 * 描述：
 */
public class UiItemImg extends ImageView {
    private Bitmap clickBitmap;
    private Bitmap unClickBitmap;
    private Paint mPaint;
    private int mAlpha;
    private Rect rect;


    public UiItemImg(Context context) {
        this(context, null, 0);
    }

    public UiItemImg(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UiItemImg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAlpha = 0;
    }

    /**
     * 传递图片资源过来
     *
     * @param clickDrawableRid   R.layout.xxx
     * @param unClickDrawableRid
     */
    public void initBitmap(int clickDrawableRid, int unClickDrawableRid) {
        //点击的图片
        clickBitmap = BitmapFactory.decodeResource(getResources(), clickDrawableRid);
        //未点击的图片
        unClickBitmap = BitmapFactory.decodeResource(getResources(), unClickDrawableRid);
        /**
         * 设置控件布局参数
         */

        setLayoutParams(new LinearLayout.LayoutParams(clickBitmap.getWidth(), clickBitmap.getHeight()));
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAlpha = 0;
    }

    public void setUiAlpha(int alpha) {
        mAlpha = alpha;
        invalidate(); //重新调用onDraw方法
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int with = unClickBitmap.getWidth();
        int height = unClickBitmap.getHeight();
        rect = new Rect(0, 0, with, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPaint != null) {
            mPaint.setAlpha(255 - mAlpha);
            canvas.drawBitmap(unClickBitmap, null, rect, mPaint);
            mPaint.setAlpha(mAlpha);
            canvas.drawBitmap(clickBitmap, null, rect, mPaint);
        }
    }
}