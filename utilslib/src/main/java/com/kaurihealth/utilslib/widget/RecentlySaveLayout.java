package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：保存病历
 */
public class RecentlySaveLayout extends LinearLayout implements View.OnClickListener {
    /**
     * 默认值
     */
    private static final int DEFAULT_TITLE_COLOR = Color.parseColor("#000b23");
    private static final int DEFAULT_CONTENT_COLOR = Color.parseColor("#ccd3d5");
    private static final String DEFAULT_CONTENT_HINT = "请输入...";

    /**
     * 布局
     */
    private TextView titleTv;
    private EditText contentTv;
    /**
     * 设置值
     */
    private String mTitle;
    private int mTitleColor;
    private int mContentColor;
    private boolean mCompileMode;
    private IEditTextClick mIEditTextClick;
    private String mHint;

    public RecentlySaveLayout(Context context) {
        this(context, null);
    }

    public RecentlySaveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecentlySaveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObtainAttributes(attrs);
        initLayoutView();
    }

    private void initObtainAttributes(AttributeSet attrs) {
        TypedArray arrayAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.RecentlyRecordLayout);
        mTitle = arrayAttrs.getString(R.styleable.RecentlyRecordLayout_RecentlyTitle);
        String content = arrayAttrs.getString(R.styleable.RecentlyRecordLayout_RecentlyContent);
        String hint = arrayAttrs.getString(R.styleable.RecentlyRecordLayout_RecentlyContentHint);
        if (hint == null) hint = DEFAULT_CONTENT_HINT;
        mHint = hint;
        mTitleColor = arrayAttrs.getColor(R.styleable.RecentlyRecordLayout_RecentlyTitleColor, DEFAULT_TITLE_COLOR);
        mContentColor = arrayAttrs.getColor(R.styleable.RecentlyRecordLayout_RecentlyContentColor, DEFAULT_CONTENT_COLOR);

        mCompileMode = arrayAttrs.getBoolean(R.styleable.RecentlyRecordLayout_RecentlyCompileMode, false);

        arrayAttrs.recycle();
    }

    private void initLayoutView() {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.include_save_recently_linear, this, true);
        titleTv = (TextView) view.findViewById(R.id.tvTitle);
        contentTv = (EditText) view.findViewById(R.id.tv_content);

        if (mTitle != null) titleTv.setText(mTitle);

        if (mCompileMode) {//不可输入
            contentTv.setHint("请点击选择");
            contentTv.setFocusable(false);
            contentTv.setOnClickListener(this);
        } else {//默认保存 false
            contentTv.setHint(mHint);
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

    public String getContent() {
        return contentTv.getText().toString().trim();
    }

    /**
     * 接口传递
     */
    public void onEditClickListener(IEditTextClick IEditTextClick) {
        this.mIEditTextClick = IEditTextClick;
    }

    @Override
    public void onClick(View v) {
        if (mCompileMode && mIEditTextClick != null) {
            mIEditTextClick.contentEditTextBack(this, contentTv.getText().toString());
        }
    }

    /**
     * 输入框内容回调
     */
    public interface IEditTextClick {
        void contentEditTextBack(View v, String content);
    }

}