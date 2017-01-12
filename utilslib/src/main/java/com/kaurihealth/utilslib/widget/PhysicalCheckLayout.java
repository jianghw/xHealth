package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaurihealth.utilslib.R;

/**
 * Created by jianghw on 2016/8/23.
 * <p/>
 * 描述：体格检查
 */
public class PhysicalCheckLayout extends LinearLayout {

    /**
     * 布局
     */
    private TextView titleTv;
    private TextView hrTv;
    private TextView bpFTv;
    private TextView bpSTv;
    private TextView rrTv;
    private TextView tTv;
    /**
     * 设置值
     */
    private String mTitle;
    private boolean mCompileMode;
    private String mContentF;
    private String mContentS;
    private boolean mBoxFMode;
    private boolean mBoxSMode;

    public PhysicalCheckLayout(Context context) {
        this(context, null);
    }

    public PhysicalCheckLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhysicalCheckLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObtainAttributes(attrs);
        initLayoutView();
    }

    private void initObtainAttributes(AttributeSet attrs) {
        TypedArray arrayAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.AuxiliaryExaminationLayout);
        mTitle = arrayAttrs.getString(R.styleable.AuxiliaryExaminationLayout_AuxiliaryTitle);
//        mContentF = arrayAttrs.getString(R.styleable.AuxiliaryExaminationLayout_AuxiliaryContentF);
//        mBoxFMode = arrayAttrs.getBoolean(R.styleable.AuxiliaryExaminationLayout_AuxiliaryBoxF, false);
//        mContentS = arrayAttrs.getString(R.styleable.AuxiliaryExaminationLayout_AuxiliaryContentS);
//        mBoxSMode = arrayAttrs.getBoolean(R.styleable.AuxiliaryExaminationLayout_AuxiliaryBoxF, false);

        mCompileMode = arrayAttrs.getBoolean(R.styleable.AuxiliaryExaminationLayout_AuxiliaryCompileMode, false);

        arrayAttrs.recycle();
    }

    private void initLayoutView() {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.include_physical_check_linear, this, true);
        titleTv = (TextView) view.findViewById(R.id.tv_title);
        hrTv = (TextView) view.findViewById(R.id.tv_hr);
        bpFTv = (TextView) view.findViewById(R.id.tv_bp_f);
        bpSTv = (TextView) view.findViewById(R.id.tv_bp_s);
        rrTv = (TextView) view.findViewById(R.id.tv_rr);
        tTv = (TextView) view.findViewById(R.id.tv_t);

        if (titleTv != null) titleTv.setText(mTitle);
    }

    public void titleText(String titleText) {
        if (titleTv != null) titleTv.setText(titleText);
    }

    public void hrText(String s) {
        if (hrTv != null) hrTv.setText(s);
    }

    public void bpFText(String s) {
        if (bpFTv != null) bpFTv.setText(s);
    }

    public void bpSText(String s) {
        if (bpSTv != null) bpSTv.setText(s);
    }

    public void rrText(String s) {
        if (rrTv != null) rrTv.setText(s);
    }

    public void tText(String s) {
        if (tTv != null) tTv.setText(s);
    }

}