package com.kaurihealth.kaurihealth.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by 张磊 on 2016/4/18.
 * 介绍：
 */
public class TagsGridview extends GridView {
    public TagsGridview(Context context) {
        super(context);
    }

    public TagsGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagsGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
