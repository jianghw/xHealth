package com.kaurihealth.utilslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
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
 * 描述：体格检查
 */
public class PhysicalCheckSaveLayout extends LinearLayout {

    /**
     * 布局
     */
    private TextView titleTv;
    private EditText hrTv;
    private EditText bpFTv;
    private EditText bpSTv;
    private EditText rrTv;
    private EditText tTv;
    /**
     * 设置值
     */
    private String mTitle;
    private boolean mCompileMode;

    public PhysicalCheckSaveLayout(Context context) {
        this(context, null);
    }

    public PhysicalCheckSaveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhysicalCheckSaveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initObtainAttributes(attrs);
        initLayoutView();
    }

    private void initObtainAttributes(AttributeSet attrs) {
        TypedArray arrayAttrs = getContext().obtainStyledAttributes(attrs, R.styleable.AuxiliaryExaminationLayout);
        mTitle = arrayAttrs.getString(R.styleable.AuxiliaryExaminationLayout_AuxiliaryTitle);
        mCompileMode = arrayAttrs.getBoolean(R.styleable.AuxiliaryExaminationLayout_AuxiliaryCompileMode, false);

        arrayAttrs.recycle();
    }

    private void initLayoutView() {
        View view = LayoutInflater.from(getContext().getApplicationContext()).inflate(R.layout.include_physical_check_save_linear, this, true);
        titleTv = (TextView) view.findViewById(R.id.tv_title);
        hrTv = (EditText) view.findViewById(R.id.edit_hr);
        bpFTv = (EditText) view.findViewById(R.id.edit_bp_f);
        bpSTv = (EditText) view.findViewById(R.id.edit_bp_s);
        rrTv = (EditText) view.findViewById(R.id.edit_rr);
        tTv = (EditText) view.findViewById(R.id.edit_t);

        if (titleTv != null) titleTv.setText(mTitle);
    }

    public void titleText(String titleText) {
        if (titleTv != null) titleTv.setText(titleText);
    }

    public String getHrText() {
        return hrTv.getText().toString().trim();
    }

    public String getBpFirText() {
        return bpFTv.getText().toString().trim();
    }

    public String getBpSecText() {
        return bpSTv.getText().toString().trim();
    }

    public String getRrText() {
        return rrTv.getText().toString().trim();
    }

    public String getTText() {
        return tTv.getText().toString().trim();
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