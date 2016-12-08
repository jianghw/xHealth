package com.kaurihealth.utilslib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.kaurihealth.utilslib.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by 张磊 on 2016/5/10.
 * 介绍：封装dialog于此
 */
public class DialogUtils {

    private static SweetAlertDialog pDialog;

    /**
     * 第三方的SweetAlertDialog提示
     *
     * @param context
     * @param message
     */
    public static void sweetDialog(Context context, String title, String message) {
        new SweetAlertDialog(context)
                .setTitleText(title)
                .setContentText(message)
                .show();
    }

    /**
     * 第三方的SweetAlertDialog 成功提示
     *
     * @param context
     * @param message
     */
    public static void sweetSuccessDialog(Context context, String message, SweetAlertDialog.OnSweetClickListener listener) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(message)
                .setConfirmClickListener(listener)
                .show();
    }

    public static void sweetMessageDialog(Context context, String title, String message) {
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setTitleText(message)
                .show();
    }


    /**
     * 第三方的SweetAlertDialog error提示
     *
     * @param context
     * @param message
     */
    public static void sweetWarningDialog(Context context, String message) {
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(context.getResources().getString(R.string.dialog_warning_title))
                .setContentText(message)
                .show();
    }

    public static void sweetProgressDialog(Context context) {
        sweetProgressDialog(context, "正在加载中...");
    }

    /**
     * 数据交互提示框
     *
     * @param context
     * @param message
     */
    public static void sweetProgressDialog(Context context, String message) {
        if (isProgressDialogAlive()) return;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.color_theme_green));
        pDialog.setTitleText(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * dismiss数据交互提示框
     */
    public static void dismissSweetProgress() {
        if (isProgressDialogAlive()) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    private static boolean isProgressDialogAlive() {
        return pDialog != null && pDialog.isShowing();
    }

    public static void changeAlertTypeWarning(Context context, String message) {
        if (isProgressDialogAlive()) {
//            pDialog.setTitleText(context.getResources().getString(R.string.dialog_warning_title))
//                    .setContentText(message)
//                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
            pDialog.dismiss();
            pDialog = null;
            sweetWarningDialog(context, message);
        } else {
            sweetWarningDialog(context, message);
        }
    }


    /**
     * 日期选择
     *
     * @param activity
     * @param listener
     */
    public static void showDateDialog(Activity activity, SelectDateDialog.DialogListener listener) {
        SelectDateDialog selectDateDialog = new SelectDateDialog(activity, listener);
        Dialog dateDialog = selectDateDialog.getDataDialog();
        dateDialog.show();
    }

    public static void showAllDateDialog(Activity activity, SelectAllDateDialog.DialogListener listener) {
        SelectAllDateDialog selectDateDialog = new SelectAllDateDialog(activity, listener);
        Dialog dateDialog = selectDateDialog.getDataDialog();
        dateDialog.show();
    }

    /**
     * 弹出选择项
     */
    public static void showStringDialog(Activity activity, String[] contentStr, PopUpNumberPickerDialog.SetClickListener setClickListener) {
        PopUpNumberPickerDialog numberPickerDialog = new PopUpNumberPickerDialog(activity, contentStr, setClickListener);
        Dialog dialog = numberPickerDialog.getDialog();
        dialog.show();
    }

    /**
     * 弹出医疗记录选择项
     */
    public static void showMedicalDialog(Activity activity, final TimeDialog.TimeSelect clickListener) {
        final String[] left = activity.getApplicationContext().getResources().getStringArray(R.array.medical_record);
        final String[] right1 = activity.getApplicationContext().getResources().getStringArray(R.array.clinical_diagnosis);
        final String[] right2 = activity.getApplicationContext().getResources().getStringArray(R.array.supplementary_test);
        final String[] right3 = activity.getApplicationContext().getResources().getStringArray(R.array.lab_test);
        final String[] right4 = activity.getApplicationContext().getResources().getStringArray(R.array.pathology);

        final TimeDialog popupDialog = new TimeDialog(activity);
        popupDialog.setLeft(left);
        popupDialog.setRight(right1);
        popupDialog.numpick_left.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setItemSecondData(newVal, popupDialog, right1, right2, right3, right4);
            }
        });
        popupDialog.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int leftPosition = popupDialog.numpick_left.getValue();
                int rightPosition = popupDialog.numpick_right.getValue();
                positonToString(leftPosition, rightPosition, clickListener, left, right1, right2, right3, right4);
                popupDialog.dismiss();
            }
        });
        popupDialog.show();
    }

    private static void positonToString(int leftPosition, int rightPosition, TimeDialog.TimeSelect clickListener, String[] left, String[] right1, String[] right2, String[] right3, String[] right4) {
        switch (leftPosition) {
            case 0:
                clickListener.selectTime(left[leftPosition], right1[rightPosition]);
                break;
            case 1:
                clickListener.selectTime(left[leftPosition], right2[rightPosition]);
                break;
            case 2:
                clickListener.selectTime(left[leftPosition], right3[rightPosition]);
                break;
            case 3:
                clickListener.selectTime(left[leftPosition], right4[rightPosition]);
                break;
            default:
                break;
        }
    }

    private static void setItemSecondData(int newVal, TimeDialog popupDialog, String[] right1, String[] right2, String[] right3, String[] right4) {
        switch (newVal) {
            case 0:
                popupDialog.setRight(right1);
                break;
            case 1:
                popupDialog.setRight(right2);
                break;
            case 2:
                popupDialog.setRight(right3);
                break;
            case 3:
                popupDialog.setRight(right4);
                break;
            default:
                break;
        }
    }

    public static Dialog createDialog(View contentView, Activity activity) {
        Dialog dialog = new Dialog(activity, R.style.customdialog);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        int screenWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        contentView.setMinimumWidth(screenWidth);
        dialog.setContentView(contentView);
        return dialog;
    }

    public static Dialog createDialogNew(View contentView, Activity activity, int style) {
        Dialog dialog = new Dialog(activity, style);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        int screenWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        contentView.setMinimumWidth(screenWidth);
        dialog.setContentView(contentView);
        return dialog;
    }

}
