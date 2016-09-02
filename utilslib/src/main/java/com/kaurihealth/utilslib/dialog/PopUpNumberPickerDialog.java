package com.kaurihealth.utilslib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.kaurihealth.utilslib.R;

import java.lang.reflect.Field;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by KauriHealth-WEBDEV on 2016/2/25.
 * 介绍：弹出数字选择框
 */
public class PopUpNumberPickerDialog {

    private Context context;
    private final Dialog dialog;

    private final TextView tvComplete;
    private final MaterialNumberPicker numberPicker;

    public PopUpNumberPickerDialog(Activity activity, String[] contentStr, final SetClickListener setClickListener) {
        this.context = activity.getApplicationContext();

        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_number_picker, null);
        tvComplete = (TextView) contentView.findViewById(R.id.tv_complete);
        numberPicker = (MaterialNumberPicker) contentView.findViewById(R.id.number_picker);

        setNumberPickerDividerColor(numberPicker, context);
        setNumberPickerRes(contentStr);

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (setClickListener != null) {
                    setClickListener.onClick(numberPicker.getValue());
                }
            }
        });

        Dialog dateDialog = new Dialog(activity, R.style.Dialog_Date);
        dateDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dateDialog.getWindow().setGravity(Gravity.BOTTOM);
        int screenWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        contentView.setMinimumWidth(screenWidth);
        dateDialog.setContentView(contentView);

        dialog = dateDialog;
    }

    public PopUpNumberPickerDialog(Activity activity, final SetClickListener setClickListener) {
        this(activity, null, setClickListener);
    }


    public void setNumberPickerRes(String[] content) {
        if (content != null) {
            numberPicker.setDisplayedValues(null);
            numberPicker.setMaxValue(content.length - 1);
            numberPicker.setMinValue(0);
            numberPicker.setValue(0);
            numberPicker.setDisplayedValues(content);
        }
    }

    public Dialog getDialog() {
        return dialog;
    }

    public interface SetClickListener {
        void onClick(int index);
    }

    /**
     * 设置numberPicker 的分割线高度好字体颜色
     *
     * @param numberPicker
     * @param context
     */
    private static void setNumberPickerDividerColor(NumberPicker numberPicker, Context context) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();

        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDividerHeight")) {
                pf.setAccessible(true);
                try {
                    int result = 2;
                    pf.set(picker, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.color_light_gray)));
                } catch (IllegalArgumentException e) {
                    Bugtags.sendException(e);
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    Bugtags.sendException(e);
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    Bugtags.sendException(e);
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
