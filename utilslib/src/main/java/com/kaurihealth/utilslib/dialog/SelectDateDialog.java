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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by KauriHealth-WEBDEV on 2016/2/24.
 * 介绍：
 */
public class SelectDateDialog {
    /**
     * 默认值
     */
    //默认40岁
    private int ageDefault = 0;
    //默认1月份
    private int monthDefault = 0;
    //默认1号
    private int dayDefault = 0;
    //默认显示的年的个数
    private int yearAmountDefault = 100;

    private String[] monthStr = new String[12];
    private String[] dayOne = new String[28];
    private String[] dayTwo = new String[29];
    private String[] dayThree = new String[30];
    private String[] dayFour = new String[31];
    private String[] years;

    private DialogListener mOnClickDialogListener;
    private Dialog dateDialog;
    private Context context;
    private MaterialNumberPicker monthPicker;
    private SuccessInterfaceM<DateBean> listener;
    private String format;

    public SelectDateDialog(Activity activity, DialogListener onClickDialogListener) {
        this.context = activity.getApplicationContext();
        this.mOnClickDialogListener = onClickDialogListener;
        init(activity);
    }

    public SelectDateDialog(Activity activity) {
        this.context = activity.getApplicationContext();
        init(activity);
    }

    public void setOnClickDialogListener(DialogListener onClickDialogListener) {
        this.mOnClickDialogListener = onClickDialogListener;
    }

    public void setOnClickListener(SuccessInterfaceM<DateBean> listener, String format) {
        this.listener = listener;
        this.format = format;
    }

    public Dialog getDataDialog() {
        return dateDialog;
    }

    private void init(Activity activity) {
        initDate(monthStr, "月");
        initDate(dayOne, "日");
        initDate(dayTwo, "日");
        initDate(dayThree, "日");
        initDate(dayFour, "日");
        years = getYears();
        Calendar cal = Calendar.getInstance();
        monthDefault = cal.get(Calendar.MONTH);
        dayDefault  = cal.get(Calendar.DAY_OF_MONTH)-1;

        setDateDialog(activity);
    }

    private void setDateDialog(Activity activity) {
        View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.dialog_date_birth, null);
        final MaterialNumberPicker yearView = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_year);
        monthPicker = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_month);
        final MaterialNumberPicker day = (MaterialNumberPicker) view.findViewById(R.id.numberpicker_day);

        setNumberPickerDividerColor(yearView, context);
        setNumberPickerDividerColor(monthPicker, context);
        setNumberPickerDividerColor(day, context);
        setNumberPickerDividerColor((NumberPicker) view.findViewById(R.id.numberpicker_year2), context);
        setNumberPickerDividerColor((NumberPicker) view.findViewById(R.id.numberpicker_year1), context);

        TextView tv_complete = (TextView) view.findViewById(R.id.tv_complete);
        tv_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int yearSelected = getSelectedYear(yearView.getValue());
                int monthSelected = Integer.valueOf(getSelectedMonth());
                int daySelected = day.getValue() + 1;
                if (SelectDateDialog.this.mOnClickDialogListener != null) {
                    SelectDateDialog.this.mOnClickDialogListener.onclick(String.valueOf(yearSelected), String.valueOf(monthSelected), String.valueOf(daySelected));
                }
                if (SelectDateDialog.this.listener != null) {
                    Calendar instance = Calendar.getInstance();
                    instance.set(Calendar.YEAR, yearSelected);
                    instance.set(Calendar.MONTH, monthSelected - 1);
                    instance.set(Calendar.DAY_OF_MONTH, daySelected);
                    instance.set(Calendar.HOUR_OF_DAY, 0);
                    instance.set(Calendar.MINUTE, 0);
                    instance.set(Calendar.SECOND, 0);
                    DateBean dateBean = new DateBean();
                    Date time = instance.getTime();
                    dateBean.date = time;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
                    dateBean.dateStr = format.format(time);
                    if (!(TextUtils.isEmpty(SelectDateDialog.this.format))) {
                        SimpleDateFormat formatShow = new SimpleDateFormat(SelectDateDialog.this.format);
                        dateBean.dateStrShow = formatShow.format(time);
                    }
                    SelectDateDialog.this.listener.success(dateBean);

                }
                dateDialog.dismiss();
            }
        });

        Dialog dialog = new Dialog(activity, R.style.Dialog_Date);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        int screenWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        view.setMinimumWidth(screenWidth);
        dialog.setContentView(view);
        dateDialog=dialog;

        setNumberPicker(yearView, years);
        //设置默认40岁
        yearView.setValue(-1);
        setNumberPicker(monthPicker, monthStr);

        //设置默认第一个月
        monthPicker.setValue(monthDefault);
        //设置默认第一个月的数据
        setNumberPicker(day, dayFour);
        //设置默认第一个月的第一天
        day.setValue(dayDefault);

        yearView.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (monthPicker.getValue() == 1) {
                    setNumberPicker(day, getDayStrOfMonth(getSelectedYear(yearView.getValue()), Integer.valueOf(getSelectedMonth())));
                }
            }
        });
        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String[] daysOfMonth = getDayStrOfMonth(getSelectedYear(yearView.getValue()), Integer.valueOf(getSelectedMonth()));
                setNumberPicker(day, daysOfMonth);
            }
        });
    }

    /**
     * 获得当前月份的字符串数组
     */
    private String[] getDayStrOfMonth(int year, int month) {
        int dayAmount = getDayOfMonth(year, month);
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
                return null;
        }
    }

    /**
     * 根据年，月  返回当前月份的天数
     */
    private int getDayOfMonth(int year, int month) {
        switch (month) {
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
                if (isLeapYear(year)) {
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
     *
     * @return boolean
     */
    private boolean isLeapYear(int year) {
        return (year / 100 == 0 && year / 400 == 0) || (year / 100 != 0 && year / 4 == 0);
    }

    /**
     * 根据在numberpicker中序号，返回选中的年
     */
    private int getSelectedYear(int index) {
        Calendar calendar = Calendar.getInstance();
        int curYear = calendar.get(Calendar.YEAR);
        int selectYear = curYear - (yearAmountDefault - 1 - index);
        return selectYear;
    }

    /**
     * 获取选中月份
     */
    private String getSelectedMonth() {
        String monthTemp = monthStr[monthPicker.getValue()];
        return monthTemp.substring(0, monthTemp.length() - 1);
    }

    //获取到当前yearAmountDefault个年
    private String[] getYears() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String[] years = new String[yearAmountDefault];
        for (int i = yearAmountDefault - 1, j = 0; i >= 0; i--, j++) {
            years[j] = String.format("%d年", year - i);
        }
        return years;
    }


    //初始化日期
    private void initDate(String[] content, String format) {
        for (int i = 0; i < content.length; i++) {
            content[i] = String.format("%d" + format, i + 1);
        }
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
        void onclick(String year, String month, String day);
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
