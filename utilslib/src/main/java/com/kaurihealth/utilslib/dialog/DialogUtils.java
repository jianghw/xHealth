package com.kaurihealth.utilslib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;

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
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * dismiss数据交互提示框
     */
    public static void dismissSweetProgress() {
        if (isProgressDialogAlive()) pDialog.dismiss();
    }

    private static boolean isProgressDialogAlive() {
        return pDialog != null && pDialog.isShowing();
    }

    public static void changeAlertTypeWarning(Context context, String message) {
        if (isProgressDialogAlive()) {
            pDialog.setTitleText(context.getResources().getString(R.string.dialog_warning_title))
                    .setContentText(message)
                    .changeAlertType(SweetAlertDialog.WARNING_TYPE);
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

    /**
     * 弹出选择项
     *
     * @param activity
     * @param contentStr
     * @param setClickListener
     */
    public static void showStringDialog(Activity activity, String[] contentStr, PopUpNumberPickerDialog.SetClickListener setClickListener) {
        PopUpNumberPickerDialog gendarDialogPopUp = new PopUpNumberPickerDialog(activity, contentStr, setClickListener);
        Dialog gendarDialog = gendarDialogPopUp.getDialog();
        gendarDialog.show();
    }

}
