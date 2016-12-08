package com.kaurihealth.kaurihealth.clinical_v.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by KauriHealth on 2016/8/18.
 */
public class MyItemListView extends ListView {
    public MyItemListView(Context context) {
        super(context);
    }

    public MyItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
