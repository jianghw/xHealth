package com.example.commonlibrary.widget.widget;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;

import com.example.commonlibrary.R;

/**
 * Created by 米平  on 2016/8/1.
 * 自定义登陆等一系列弹窗。
 */
public class CommonDialog{
    //自定义AlerDialog
    public static AlertDialog getInstance(Activity context,View view_dialog){
//        LayoutInflater inflater = LayoutInflater.from(context);
//        view_dialog = inflater.inflate(R.layout.alertdialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogStyle);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view_dialog);
        alertDialog.getWindow().setContentView(view_dialog);
        alertDialog.getWindow().getAttributes().alpha =0.7f;
        int width = context.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        int heigth = context.getWindow().getWindowManager().getDefaultDisplay().getHeight();
        alertDialog.getWindow().setLayout(width / 3, heigth / 12);
        return  alertDialog;
    }

    //自定义Dialog
    public static Dialog getDialogInstance(Activity context,View alertdialog){
        Dialog dialog = new Dialog(context,R.style.MyDialogStyle);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
       int width = context.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        int height = context.getWindow().getWindowManager().getDefaultDisplay().getHeight();
        dialog.setContentView(alertdialog);
        attributes.width = width/3;
        attributes.height =height/7;
        attributes.alpha = 0.5f;
        dialog.getWindow().setAttributes(attributes);
        return dialog;
    }
}
