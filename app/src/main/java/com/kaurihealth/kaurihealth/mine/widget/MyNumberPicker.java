package com.kaurihealth.kaurihealth.mine.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.NumberPicker;

import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;

/**
 * Created by 张磊 on 2016/7/14.
 * 介绍：
 */
public class MyNumberPicker extends NumberPicker {
    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());
    private int Click = 5;
    private int ClickTime = 5;

    public MyNumberPicker(Context context) {
        super(context);
    }

    public MyNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View selectdView = getSelectdView();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_UP:
                return super.onTouchEvent(event);
            default:
                return super.onTouchEvent(event);
        }
    }

    /**
     * 获得当前选中的view
     */
    private View getSelectdView() {
        int childCount = getChildCount();
        int selctPosition;
        if (childCount % 2 != 0) {
            selctPosition = (childCount - 1) / 2 + 1;
        } else {
            return null;
        }
        View selectdView = getChildAt(selctPosition);
        return selectdView;
    }
}
