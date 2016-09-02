package com.example.commonlibrary.widget.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by 张磊 on 2016/6/3.
 * 介绍：
 */
public class EittextTextUtils {
    //获取限制小数位数的textchanggelistener
    public static void registTextwatch(final EditText editText) {
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nS = s.toString().trim();
                if (nS.contains(".")) {
                    if (nS.length() - 1 - nS.toString().indexOf(".") > 2) {
                        nS = nS.substring(0, nS.indexOf(".") + 3);
                        editText.setText(nS);
                        editText.setSelection(nS.length());
                    }
                    if (nS.toString().indexOf(".") > 7) {
                        nS = nS.substring(0, 7) + nS.substring(nS.toString().indexOf("."), nS.indexOf(".") + 3);
                        editText.setText(nS);
                        editText.setSelection(nS.length());
                    }
                } else if (nS.length() > 7) {
                    nS = nS.substring(0, 7);
                    editText.setText(nS);
                    editText.setSelection(nS.length());
                }

                if (nS.substring(0).equals(".")) {
                    nS = "0" + nS;
                    editText.setText(nS);
                    editText.setSelection(nS.length());
                }
                if (nS.startsWith("0") && nS.length() > 1) {
                    if (!nS.substring(1, 2).equals(".")) {
                        nS = nS.substring(0, 1);
                        editText.setText(nS);
                        editText.setSelection(nS.length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        editText.addTextChangedListener(textWatcher);
    }
}
