package com.kaurihealth.utilslib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.kaurihealth.utilslib.R;

import java.lang.reflect.Field;
import java.util.Calendar;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * 介绍：
 */
public class SelectAllDateDialog {
    //默认月份
    private int monthDefault;
    private int dayDefault;
    private int hourDefault;
    private int minuteDefault;

    //默认显示的年的个数
    private int yearAmount = 90;

    private String[] monthArrary = new String[12];
    private String[] dayOne = new String[28];
    private String[] dayTwo = new String[29];
    private String[] dayThree = new String[30];
    private String[] dayFour = new String[31];
    private String[] hourArray = new String[24];
    private String[] minuteArray = new String[60];
    private String[] yearArrary;
    private DialogListener mOnClickDialogListener;

    private Context context;
    private Dialog mDateDialog;

    public SelectAllDateDialog(Activity activity, DialogListener onClickDialogListener) {
        this.context = activity.getApplicationContext();
        this.mOnClickDialogListener = onClickDialogListener;
        init(activity);
    }

    private void init(Activity activity) {
        yearArrary = getYearArrary();
        initDate(monthArrary, "月");
        initDate(dayOne, "日");
        initDate(dayTwo, "日");
        initDate(dayThree, "日");
        initDate(dayFour, "日");
        initHour(hourArray, "时");
        initHour(minuteArray, "分");

        Calendar calendar = Calendar.getInstance();
        monthDefault = calendar.get(Calendar.MONTH);
        dayDefault = calendar.get(Calendar.DAY_OF_MONTH) - 1;
        hourDefault = calendar.get(Calendar.HOUR_OF_DAY);
        minuteDefault = calendar.get(Calendar.MINUTE);

        setDateDialog(activity);
    }

    //获取到当前yearAmountDefault个年
    private String[] getYearArrary() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String[] years = new String[yearAmount];
//        for (int i = yearAmount - 1, j = 0; i >= 0; i--, j++) {
//            years[j] = String.format("%d年", year - i);
//        }
        for (int i = 0, j = 0; i < yearAmount; i++, j++) {
            years[j] = String.format("%d年", year + i);
        }

        return years;
    }

    //初始化日期
    private void initDate(String[] content, String format) {
        for (int i = 0; i < content.length; i++) {
            content[i] = String.format("%d" + format, i + 1);
        }
    }

    private void initHour(String[] content, String format) {
        for (int i = 0; i < content.length; i++) {
            content[i] = String.format("%02d" + format, i);
        }
    }

    private void setDateDialog(Activity activity) {
        View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.dialog_date_all, null);

        final MaterialNumberPicker yearPicker = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_year);
        final MaterialNumberPicker monthPicker = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_month);
        final MaterialNumberPicker dayPicker = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_day);

        MaterialNumberPicker hourPicker = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_hour);
        MaterialNumberPicker minutePicker = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_minute);

        setNumberPickerDividerColor(yearPicker, context);
        setNumberPickerDividerColor(monthPicker, context);
        setNumberPickerDividerColor(dayPicker, context);

        setNumberPickerDividerColor(hourPicker, context);
        setNumberPickerDividerColor(minutePicker, context);

        TextView tv_complete = (TextView) view.findViewById(R.id.tv_complete);

        Dialog dialog = new Dialog(activity, R.style.Dialog_Date);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        int screenWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        view.setMinimumWidth(screenWidth);
        dialog.setContentView(view);
        mDateDialog = dialog;

        completeSelect(tv_complete, yearPicker, monthPicker, dayPicker, hourPicker, minutePicker, dialog);

        setNumberPicker(yearPicker, yearArrary);
        yearPicker.setValue(yearAmount);

        setNumberPicker(monthPicker, monthArrary);
        monthPicker.setValue(monthDefault);

        //设置默认第一个月的数据
        setNumberPicker(dayPicker, dayFour);
        dayPicker.setValue(dayDefault);

        setNumberPicker(hourPicker, hourArray);
        hourPicker.setValue(hourDefault);

        setNumberPicker(minutePicker, minuteArray);
        minutePicker.setValue(minuteDefault);

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal != oldVal) {
                    setNumberPicker(dayPicker, getDayStrOfMonth(yearArrary[newVal], monthArrary[monthPicker.getValue()]));
                }
            }
        });
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal != oldVal) {
                    setNumberPicker(dayPicker, getDayStrOfMonth(yearArrary[yearPicker.getValue()], monthArrary[newVal]));
                }
            }
        });
    }

    public Dialog getDataDialog() {
        return mDateDialog;
    }

    private void completeSelect(TextView tv_complete, final MaterialNumberPicker yearPicker,
                                final MaterialNumberPicker monthPicker, final MaterialNumberPicker dayPicker,
                                final MaterialNumberPicker hourPicker, final MaterialNumberPicker minutePicker, final Dialog dateDialog) {
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yearSelected = yearArrary[yearPicker.getValue()];
                String monthSelected = monthArrary[monthPicker.getValue()];
                String daySelected = String.format("%d" + "日", dayPicker.getValue() + 1);
                String hourSelected = hourArray[hourPicker.getValue()];
                String minuteSelected = minuteArray[minutePicker.getValue()];

                mOnClickDialogListener.onclick(yearSelected, monthSelected, daySelected, hourSelected, minuteSelected);
                dateDialog.cancel();
                dateDialog.dismiss();
            }
        });
    }


    /**
     * 根据在numberpicker中序号，返回选中的年
     */
    private int getSelectedYear(int index) {
        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int selectYear = curYear - (yearAmount - 1 - index);
        return selectYear;
    }

    /**
     * 获得当前月份的字符串数组
     */
    private String[] getDayStrOfMonth(String year, String month) {
        int dayAmount = 31;
        try {
            dayAmount = getDayOfMonth(year, month);
        } catch (Exception e) {
            e.printStackTrace();
        }
        switch (dayAmount) {
            case 28:
                return dayOne;
            case 29:
                return dayTwo;
            case 30:
                return dayThree;
            case 31:
                return dayFour;
            default:
                return dayFour;
        }
    }

    /**
     * 根据年，月  返回当前月份的天数
     */
    private int getDayOfMonth(String year, String month) {
        if (TextUtils.isEmpty(month) || TextUtils.isEmpty(year)) return 0;
        int mMonth = Integer.valueOf(month.contains("月") ? month.split("月")[0]: month);
        int mYear = Integer.valueOf(year.contains("年") ? year.split("年")[0]: year);
        switch (mMonth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (isLeapYear(mYear)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 0;
        }
    }

    /**
     * 闰年：29 平年：28
     * 返回当前是否是闰年
     */
    private boolean isLeapYear(int year) {
        return ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0));
    }

    private static void setNumberPicker(MaterialNumberPicker numberpicker, String[] content) {
        if (content != null) {
            numberpicker.setDisplayedValues(null);
            numberpicker.setMaxValue(content.length - 1);
            numberpicker.setMinValue(0);
            numberpicker.setValue(0);
            numberpicker.setDisplayedValues(content);
        }
    }

    public interface DialogListener {
        void onclick(String year, String month, String day, String hour, String minute);
    }

    /**
     * 设置numberPicker 的分割线高度好字体颜色
     */
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, Context context) {
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
