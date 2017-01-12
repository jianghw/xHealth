package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

import com.daimajia.swipe.SwipeLayout;

/**
 * Created by jianghw on 2017/1/3.
 * <p/>
 * Describe:
 */

public class SwipeDeleteLayout extends SwipeLayout {
    private ViewGroup parent;

    public SwipeDeleteLayout(Context context) {
        super(context);
    }

    public SwipeDeleteLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeDeleteLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setConflictParent(ViewGroup parent) {
        this.parent = parent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(arg0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(arg0);
    }
}
