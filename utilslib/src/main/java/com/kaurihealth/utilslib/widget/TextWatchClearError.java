package com.kaurihealth.utilslib.widget;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 张磊 on 2016/7/7.
 * 介绍：
 */
public class TextWatchClearError implements TextWatcher {
    private TextView mEditText;
    private ImageView mImageView;


    public TextWatchClearError(TextView editText, ImageView imageView) {
        mEditText = editText;
        mImageView = imageView;

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText(null);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        mImageView.setVisibility(View.GONE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
//        if (s.length()>=1) {
//            if (editText instanceof TextInputEditText) {
//                ViewParent parent = editText.getParent();
//                if (parent instanceof TextInputLayout) {
//                    ((TextInputLayout) parent).setErrorEnabled(false);
//                }
//            }
//        }
//        if (s.length() == 0) {
//            imageView.setVisibility(View.GONE);
//        } else {
//            imageView.setVisibility(View.VISIBLE);
//        }
    }
}
