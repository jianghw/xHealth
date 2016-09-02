package com.kaurihealth.kaurihealth.util;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by 张磊 on 2016/7/7.
 * 介绍：
 */
public class TextWatchClearError implements TextWatcher {
    private EditText editText;
    private ImageView imageView;



    public TextWatchClearError(EditText editText, ImageView imageView) {
        this.editText = editText;
        this.imageView =imageView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        imageView.setVisibility(View.GONE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
//        if (!(editText.equals(""))) {
//            imageView.setVisibility(View.VISIBLE);
//        }else {
//            imageView.setVisibility(View.GONE);
//        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length()>=1) {
            if (editText instanceof TextInputEditText) {
                ViewParent parent = editText.getParent();
                if (parent instanceof TextInputLayout) {
                    ((TextInputLayout) parent).setErrorEnabled(false);
                }
            }
        }
        if (s.length() == 0) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }

    }
}
