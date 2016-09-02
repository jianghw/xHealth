package com.youyou.zllibrary.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;


import com.bugtags.library.Bugtags;
import com.youyou.zllibrary.R;

import java.lang.reflect.Field;

/**
 * 创建时间: 2016/1/6
 * 创建人:   张磊
 * 描述:
 * 修订时间:
 * 使用说明:
 */
public class TimeDialog extends Dialog {

    public final QNumberPicker numpick_left;
    public final QNumberPicker numpick_right;
    public final TextView btnConfirm;
    int dateSelect = 0;
    int mtimeSelect = 0;

    public TimeDialog(final Context context) {
        super(context, R.style.dialog);
        View view = LayoutInflater.from(context).inflate(R.layout.timelay, null);
        setContentView(view);
        numpick_left = (QNumberPicker) view.findViewById(R.id.numpick_data);
        numpick_right = (QNumberPicker) view.findViewById(R.id.numpick_time);
        setNumberPickerDividerColor(numpick_left,context);
        setNumberPickerDividerColor(numpick_right,context);
        numpick_left.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mtimeSelect = newVal;
            }
        });
        numpick_right.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dateSelect = newVal;
            }
        });
        numpick_right.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numpick_left.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        Window window = this.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        layoutParams.width = width;
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle);  //添加动画
        ImageView btn_candle = (ImageView) view.findViewById(R.id.btn_cancle);
        btn_candle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeDialog.this.dismiss();
            }
        });
        btnConfirm = (TextView) view.findViewById(R.id.btn_confirm);
    }

/*    public void setConfirmListener(final TimeSelect timeSelect) {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dates != null && dates.length != 0 && times != null && times.length != 0) {
                    timeSelect.selectTime(dateSelect, mtimeSelect);
                    TimeDialog.this.dismiss();
                } else {
                    dismiss();
                }
            }
        });
    }*/

    String[] dates;

    public void setLeft(String[] dates) {
        this.dates = dates;
        numpick_left.setDisplayedValues(dates);
        numpick_left.setMinValue(0);
        numpick_left.setMaxValue(dates.length - 1);
        numpick_left.setValue(dateSelect);
    }

    String[] times;

    public void setRight(String[] times) {
        this.times = times;
        numpick_right.setValue(mtimeSelect);
        numpick_right.setMinValue(0);
        numpick_right.setDisplayedValues(null);
        numpick_right.setMaxValue(times.length - 1);
        numpick_right.setDisplayedValues(times);
    }

/*    public void clearLeft() {
//        this.times=null;
//        numpick_right.setDisplayedValues(null);
    }

    public void clearRight() {
//        this.dates=null;
//        numpick_left.setDisplayedValues(null);
    }*/

    private static void setNumberPickerDividerColor(NumberPicker numberPicker, Context context) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();

        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDividerHeight")) {
                pf.setAccessible(true);
                try {
                    int result = 2;
                    pf.set(picker, result);
                } catch (Exception e){
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
                    pf.set(picker, new ColorDrawable(context.getResources().getColor(R.color.gray)));
                } catch (IllegalArgumentException e) { Bugtags.sendException(e);
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) { Bugtags.sendException(e);
                    e.printStackTrace();
                } catch (IllegalAccessException e) { Bugtags.sendException(e);
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public interface TimeSelect {
        void selectTime(int  date, int time);
    }
}
