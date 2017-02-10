package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：辅助检查
 */
public class AuxiliaryExaminationLayout extends LinearLayout {
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
    private TextView contentFTv;
    private TextView contentSTv;
    private CheckBox boxFTv;
    private CheckBox boxSTv;
    /**
     * 设置值
     */
    private String mTitle;
    private boolean mCompileMode;
    private String mContentF;
    private String mContentS;
    private boolean mBoxFMode;
    private boolean mBoxSMode;

    public AuxiliaryExaminationLayout(Context context) {
        this(context, null);
    }

    public AuxiliaryExaminationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AuxiliaryExaminationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObtainAttributes(attrs);
        initLayoutView();
    }

    private void initObtainAttributes(AttributeSet attrs) {
        TypedArray arrayAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.AuxiliaryExaminationLayout);
        mTitle = arrayAttrs.getString(R.styleable.AuxiliaryExaminationLayout_AuxiliaryTitle);
        mContentF = arrayAttrs.getString(R.styleable.AuxiliaryExaminationLayout_AuxiliaryContentF);
        mBoxFMode = arrayAttrs.getBoolean(R.styleable.AuxiliaryExaminationLayout_AuxiliaryBoxF, false);
        mContentS = arrayAttrs.getString(R.styleable.AuxiliaryExaminationLayout_AuxiliaryContentS);
        mBoxSMode = arrayAttrs.getBoolean(R.styleable.AuxiliaryExaminationLayout_AuxiliaryBoxF, false);

        mCompileMode = arrayAttrs.getBoolean(R.styleable.AuxiliaryExaminationLayout_AuxiliaryCompileMode, false);

        arrayAttrs.recycle();
    }

    private void initLayoutView() {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.include_auxiliary_examination_linear, this, true);
        titleTv = (TextView) view.findViewById(R.id.tv_title);
        contentFTv = (TextView) view.findViewById(R.id.tv_auxiliary_f);
        boxFTv = (CheckBox) view.findViewById(R.id.cb_auxiliary_f);
        contentSTv = (TextView) view.findViewById(R.id.tv_auxiliary_s);
        boxSTv = (CheckBox) view.findViewById(R.id.cb_auxiliary_s);

        if (titleTv != null) titleTv.setText(mTitle);
        if (contentFTv != null) contentFTv.setText(mContentF);
        if (contentSTv != null) contentSTv.setText(mContentS);
    }

    public void titleText(String titleText) {
        if (titleTv != null) titleTv.setText(titleText);
    }

    public void boxFirstChecked(boolean checked) {
        if (boxFTv != null) boxFTv.setChecked(checked);
    }

    public boolean getBoxFirst() {
        return boxFTv.isChecked();
    }

    public void boxSecondChecked(boolean checked) {
        if (boxSTv != null) boxSTv.setChecked(checked);
    }

    public boolean getBoxSecond() {
        return boxSTv.isChecked();
    }

    public void setEnableFalse(boolean checked) {
        if (boxFTv != null) boxFTv.setEnabled(checked);
        if (boxSTv != null) boxSTv.setEnabled(checked);
    }


}