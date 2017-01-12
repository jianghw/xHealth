package com.kaurihealth.utilslib.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;

import java.util.List;

/**
 * Created by jianghw on 2016/12/15.
 * <p/>
 * Describe:
 */

public class SearchTipsGroupView extends LinearLayout {
    /**
     * 最大布局宽度
     */
    private int maxLineWidth;

    public SearchTipsGroupView(Context context) {
        this(context, null);
    }

    public SearchTipsGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchTipsGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.VERTICAL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SearchTipsGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int layoutWidth = MeasureSpec.getSize(widthMeasureSpec);
        maxLineWidth = layoutWidth - getPaddingLeft() - getPaddingRight();
    }

    public void initViews(List<String> strings) {
        if(strings.isEmpty()){
            strings.add(0,"暂无疾病数据");
        }
     int curLyWidth = 0;
        int size = strings.size();
        boolean isNewLine = true;
        LinearLayout linearLayout=null;
        LayoutParams layoutParams=null;

        for (int i = 0; i < size; i++) {
            if (isNewLine) {
                linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = 10;
            }

            View view = LayoutInflater.from(getContext()).inflate(R.layout.search_item_text, null);
            TextView itemView = (TextView) view.findViewById(R.id.textView);
            itemView.setText(strings.get(i)!=null? strings.get(i):"暂无数据");
            LayoutParams itemParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemParams.topMargin = 10;

            curLyWidth += 10 + getViewWidth(itemView);
            if (curLyWidth > maxLineWidth) {
                curLyWidth = 0;
                addView(linearLayout, layoutParams);
                isNewLine = true;
                i--;
            } else {
                isNewLine = false;
                linearLayout.addView(view,itemParams);
            }
        }
        addView(linearLayout,layoutParams);
    }

    private int getViewWidth(TextView view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

}
