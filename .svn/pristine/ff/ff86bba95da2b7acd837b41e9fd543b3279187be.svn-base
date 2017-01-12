package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：病历详情中 最近病历
 */
public class RecentlyRecordLayout extends LinearLayout {
    /**
     * 默认值
     */
    private static final String DEFAULT_TITLE = "标题：";
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#ccd3d5");
    private static final int DEFAULT_CONTENT_COLOR = Color.parseColor("#333333");
    private static final int DEFAULT_COMPILE_TITLE_COLOR = Color.parseColor("#000b23");
    private static final int DEFAULT_COMPILE_CONTEN_COLOR = Color.parseColor("#293d47");
    /**
     * 布局
     */
    private TextView titleTv;
    private TextView contentTv;
    /**
     * 设置值
     */
    private String mTitle;
    private int mTitleColor;
    private int mContentColor;
    private boolean mCompileMode;

    public RecentlyRecordLayout(Context context) {
        this(context, null);
    }

    public RecentlyRecordLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentlyRecordLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObtainAttributes(attrs);
        initLayoutView();
    }

    private void initObtainAttributes(AttributeSet attrs) {
        TypedArray arrayAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.RecentlyRecordLayout);
        String title = arrayAttrs.getString(R.styleable.RecentlyRecordLayout_RecentlyTitle);
        mTitle = title;
        String content = arrayAttrs.getString(R.styleable.RecentlyRecordLayout_RecentlyContent);

        mTitleColor = arrayAttrs.getColor(R.styleable.RecentlyRecordLayout_RecentlyTitleColor, DEFAULT_TITLE_COLOR);
        mContentColor = arrayAttrs.getColor(R.styleable.RecentlyRecordLayout_RecentlyContentColor, DEFAULT_CONTENT_COLOR);

        mCompileMode = arrayAttrs.getBoolean(R.styleable.RecentlyRecordLayout_RecentlyCompileMode, false);

        arrayAttrs.recycle();
    }

    private void initLayoutView() {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.include_record_recently_linear, this, true);
        titleTv = (TextView) view.findViewById(R.id.tvTitle);
        contentTv = (TextView) view.findViewById(R.id.tv_content);

        if (mTitle != null) titleTv.setText(mTitle);
        if (mCompileMode) {//编辑
            titleTv.setTextColor(DEFAULT_COMPILE_TITLE_COLOR);
            contentTv.setTextColor(DEFAULT_COMPILE_CONTEN_COLOR);
        } else {
            titleTv.setTextColor(mTitleColor);
            contentTv.setTextColor(mContentColor);
        }
    }

    public void titleText(String titleText) {
        if (titleTv != null) titleTv.setText(titleText);
    }

    public void contentText(String contentText) {
        if (contentTv != null) contentTv.setText(contentText);
    }

    public void setContentTitle(String titleText, String contentText) {
        titleText(titleText);
        contentText(contentText);
    }

    public String getContentText(){
        return contentTv.getText().toString().trim();
    }

}