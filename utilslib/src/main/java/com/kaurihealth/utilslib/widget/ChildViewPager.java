package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.graphics.PointF;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：绝决嵌套viewpager问题
 */
public class ChildViewPager extends ViewPager {
    /**
     * 触摸时按下的点
     **/
    PointF downP = new PointF();
    /**
     * 触摸时当前的点
     **/
    PointF curP = new PointF();

    private int vpWith;

    public ChildViewPager(Context context) {
        super(context);
    }

    public ChildViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        // TODO Auto-generated method stub
//        // 当拦截触摸事件到达此位置的时候，返回true，
//        // 说明将onTouch拦截在此控件，进而执行此控件的onTouchEvent
//        return true;
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        vpWith = w;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (getChildCount() <= 1) {
//            return super.onTouchEvent(event);
//        }
        //获取当前点位置
        curP.x = event.getX();
        curP.y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /**
                 操控父元素不拦截ACTION_DOWN,因为ACTION_DOWN不受 ACTION_DISALLOW_INTERCEPT 标记控制,
                 所以一旦父元素拦截ACTION_DOWN,这个事件系列都会被交由父元素处理.
                 */
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaX = curP.x - downP.x;
                float deltaY = curP.y - downP.y;
                if (deltaX >= vpWith * 3 / 4) {
                    //让父元素可以继续拦截MOVE事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        downP.x = curP.x;
        downP.y = curP.y;
        return super.dispatchTouchEvent(event);
    }
}