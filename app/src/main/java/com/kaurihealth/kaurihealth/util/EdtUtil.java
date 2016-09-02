package com.kaurihealth.kaurihealth.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by KauriHealth-WEBDEV on 2016/2/4.
 * 介绍：
 */
public class EdtUtil {
    /**
     * 监控editText，当editeText发生变化的时候，清空提示内容
     */
    public static void monitorEdittext(EditText editText, final TextView[] textViews) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                for (TextView item : textViews) {
                    item.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
