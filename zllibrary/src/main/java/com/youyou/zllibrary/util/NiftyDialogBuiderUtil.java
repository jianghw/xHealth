package com.youyou.zllibrary.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

/**
 * Created by 米平 on 2016/7/5.
 */
public class NiftyDialogBuiderUtil {


    private static NiftyDialogBuilder dialogBuilder;
    private static TextView tv;


    private static void init(Context context, String tvText) {
        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        tv = new TextView(context);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setGravity(View.SCROLL_INDICATOR_TOP);
        tv.setText(tvText);

    }

    //展示错误的信息
    public static void showErrorDialog(Context context, String title,
                                       String message, int duration, Effectstype effectstype,
                                       String tvText) {

        init(context,tvText);
        dialogBuilder
                .withTitle(title)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessage(message)
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#50d2c2")
                .withDuration(duration)
                .withEffect(effectstype)
                .withButton1Text("OK")
                .isCancelableOnTouchOutside(true)
                .setCustomView(tv, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                       // Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
                    }
                })

                .show();
    }


}
