package com.kaurihealth.kaurihealth.mine.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.mine.bean.DialogParam;

import java.util.List;


/**
 * Created by 张磊 on 2016/7/12.
 * 介绍：
 */
public class NumberPickerDialog extends Dialog {
    private static final String confirmTextDefault = "确定";
    private static final String cancleTextDefault = "取消";
    private NumberPicker numberPicker;
    private String[] strings;
    private TextView tvCancle;
    private TextView tvConfirm;
    private Context context;
    private View view;

    public NumberPickerDialog(Context context) {
        super(context);
        init(context);
    }

    public NumberPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);
        int screenWidth = window.getWindowManager().getDefaultDisplay().getWidth();
        view.setMinimumWidth(screenWidth);
        setContentView(view);
    }

    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.choosebankcard_dialog, null);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPickerContent);
        SelectDateDialog.setNumberPickerDividerColor(numberPicker,context);
        tvCancle = (TextView) view.findViewById(R.id.tvCancle);
        tvConfirm = (TextView) view.findViewById(R.id.tvConfirm);
    }


    public static class Builder {
        private DialogParam p = new DialogParam();
        public NumberPickerDialog dialog;

        public Builder(@NonNull Context context, @StyleRes int style) {
            p.context = context;
            p.style = style;
        }

        public Builder(@NonNull Context context) {
            this(context, R.style.Dialog_Date);
        }


        public Builder setNegativeButton(String cancleText, SuccessInterfaceM<Integer> cancleOnClickListener) {
            p.cancleText = cancleText;
            p.cancleOnclickListener = cancleOnClickListener;
            return this;
        }

        public Builder setNegativeButton(@StringRes int cancleText, SuccessInterfaceM<Integer> cancleOnClickListener) {
            return setNegativeButton(p.context.getString(cancleText), cancleOnClickListener);
        }

        public Builder setNegativeButton(SuccessInterfaceM<Integer> cancleOnClickListener) {
            return setNegativeButton(cancleTextDefault, cancleOnClickListener);
        }

        public Builder setPositiveButton(String confirmText, SuccessInterfaceM<Integer> confirmOnclickListener) {
            p.confirmText = confirmText;
            p.confirmOnclickListener = confirmOnclickListener;
            return this;
        }

        public Builder setPositiveButton(@StringRes int confirmText, SuccessInterfaceM<Integer> confirmOnclickListener) {
            return setPositiveButton(p.context.getString(confirmText), confirmOnclickListener);
        }

        public Builder setPositiveButton(SuccessInterfaceM<Integer> confirmOnclickListener) {
            return setPositiveButton(confirmTextDefault, confirmOnclickListener);
        }

        public Builder setItems(String[] iteams) {
            p.iteams = iteams;
            return this;
        }

        public Builder setItems(List<String> iteams) {
            String[] iteamStrs = new String[iteams.size()];
            if (iteams != null) {
                for (int i = 0; i < iteams.size(); i++) {
                    iteamStrs[i] = iteams.get(i);
                }
            }
            p.iteams = iteamStrs;
            return this;
        }

        public Builder setItems(@ArrayRes int iteams) {
            return setItems(p.context.getResources().getStringArray(iteams));
        }

        public Dialog create() {
            if (p.style != p.ErrorDefault) {
                dialog = new NumberPickerDialog(p.context, p.style);
            } else {
                dialog = new NumberPickerDialog(p.context);
            }
            if (!TextUtils.isEmpty(p.cancleText)) {
                dialog.tvCancle.setText(p.cancleText);
            }
            if (p.cancleOnclickListener == null) {
                dialog.tvCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            } else {
                dialog.tvCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        p.cancleOnclickListener.success(dialog.numberPicker.getValue());
                    }
                });
            }
            if (!TextUtils.isEmpty(p.confirmText)) {
                dialog.tvConfirm.setText(p.confirmText);
            }
            if (p.confirmOnclickListener == null) {
                dialog.tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            } else {
                dialog.tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        p.confirmOnclickListener.success(dialog.numberPicker.getValue());
                    }
                });
            }
            if (p.iteams != null) {
                dialog.numberPicker.setMinValue(0);
                dialog.numberPicker.setMaxValue(p.iteams.length - 1);
                dialog.numberPicker.setDisplayedValues(p.iteams);
            }

            return dialog;
        }
    }
}
