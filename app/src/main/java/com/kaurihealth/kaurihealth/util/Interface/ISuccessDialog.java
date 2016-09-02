package com.kaurihealth.kaurihealth.util.Interface;

import android.view.View;

/**
 * Created by 张磊 on 2016/7/15.
 * 介绍：
 */
public interface ISuccessDialog {
    void setTitle(String title);

    void setMes(String mes);

    void setConfirm(String btnConirm, View.OnClickListener onClickListener);

    void setConfirm(View.OnClickListener onClickListener);
}
