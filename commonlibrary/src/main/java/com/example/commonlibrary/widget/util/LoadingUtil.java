package com.example.commonlibrary.widget.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.commonlibrary.R;
import com.example.commonlibrary.widget.widget.CommonDialog;

/**
 * Created by KauriHealth-WEBDEV on 2016/2/15.
 * 介绍：
 */
public class LoadingUtil {
    private static Dialog alertDialog;
    private static LoadingUtil instance;
    private String messageDefault;
    private SuccessInterface dismissListener;
    private Handler handler = new Handler();

    private View view_dialog;
    private TextView tv_dialog;

    private LoadingUtil() {
    }

    public static LoadingUtil getInstance(Activity context) {
        instance = new LoadingUtil();
        instance.init(context);
        return instance;
    }

    public static Dialog getInstance(){
        return alertDialog;
    }

    public void init(Activity context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view_dialog = inflater.inflate(R.layout.alertdialog_new, null);
        tv_dialog = (TextView) view_dialog.findViewById(R.id.tv_dialog);
        //新添加的dialog
        alertDialog = CommonDialog.getDialogInstance(context, view_dialog);
        alertDialog.setCancelable(false);
        messageDefault = context.getResources().getString(R.string.messageDefault_new);
    }

    public void show() {
        if (alertDialog != null) {
            alertDialog.show();
            tv_dialog.setText(messageDefault);
        }
    }

    public void show(String message) {
        if (alertDialog != null) {
            alertDialog.show();
            tv_dialog.setText(message);
        }
    }


    public void setDismissDelayTime(int dismissDelayTime) {
        this.dismissDelayTime = dismissDelayTime;
    }

    private int dismissDelayTime = 800;

    public void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    /**
     * 显示提示之后，过段时间loading消失
     *
     * @param message
     */
    public void dismiss(String message) {
        dismiss(message, null);
    }

    public void dismiss(String message, final Success success) {
        if (alertDialog != null) {
            tv_dialog.setText(message);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (success != null) {
                        success.dismiss();

                    }
                    alertDialog.dismiss();
                    tv_dialog.setText(messageDefault);
                }
            }, dismissDelayTime);

        }
    }

    public interface Success {
        void dismiss();
    }

    public void setMessage(String message) {
        if (alertDialog != null) {
            messageDefault = message;
            tv_dialog.setText(message);
        }
    }

    int num = 0;

    public void regist() {
        num++;
    }

    public void unregist() {
        num--;
        if (num <= 0) {
            num = 0;
        }
        if (num == 0) {
            instance.dismiss();
            if (this.dismissListener != null) {
                this.dismissListener.success();
            }
        }
    }

    public void setDismissListener(SuccessInterface dismissListener) {
        this.dismissListener = dismissListener;
    }
}
