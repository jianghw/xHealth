package com.kaurihealth.kaurihealth.util;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.kaurihealth.kaurihealth.open_account_v.OpenAnAccountActivity;
import com.kaurihealth.utilslib.R;

/**
 * Created by mip .
 * 介绍：
 */
public class TextListener implements TextWatcher {
    private TextView mEditText;
    private TextView mTextView;
    private int mGreyIcon;
    private int mGreenIcon;
    private Context context;


    public TextListener(TextView editText, TextView textView, int greyIcon, int greenIcon, final Context context) {
        mEditText = editText;
        this.mTextView = textView;
        this.mGreenIcon = greenIcon;
        this.mGreyIcon = greyIcon;
        this.context = context;

        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Drawable drawableLeft = context.getResources().getDrawable(mGreenIcon);
                    drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                    mEditText.setCompoundDrawables(drawableLeft, null, null, null);

                    mTextView.setBackgroundResource(R.color.color_enable_green);
                } else if (mEditText.getText().toString().length() > 0) {
                    Drawable drawableLeft = context.getResources().getDrawable(mGreenIcon);
                    drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                    mEditText.setCompoundDrawables(drawableLeft, null, null, null);

                    mTextView.setBackgroundResource(R.color.color_enable_green);
                } else {
                    Drawable drawableLeft = context.getResources().getDrawable(mGreyIcon);
                    drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                    mEditText.setCompoundDrawables(drawableLeft, null, null, null);

                    mTextView.setBackgroundResource(R.color.color_text_gray);
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        ((OpenAnAccountActivity)(context)).setComplete();
    }
}
