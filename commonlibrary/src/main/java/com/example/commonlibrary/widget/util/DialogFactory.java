package com.example.commonlibrary.widget.util;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by 张磊 on 2016/4/15.
 * 介绍：
 */
public class DialogFactory {
    public static Dialog createDialog(View contentView, Activity activity, int style) {
        Dialog dialog = new Dialog(activity, style);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        int screenWidth = activity.getWindow().getWindowManager().getDefaultDisplay().getWidth();
        contentView.setMinimumWidth(screenWidth);
        dialog.setContentView(contentView);
        return dialog;
    }

}
