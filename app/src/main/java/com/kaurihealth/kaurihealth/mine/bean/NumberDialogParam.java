package com.kaurihealth.kaurihealth.mine.bean;

import android.content.Context;

import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;

/**
 * Created by 张磊 on 2016/7/13.
 * 介绍：
 */
public class NumberDialogParam {
    public final int ErrorDefault = -100;
    public Context context;
    public SuccessInterfaceM<SelectBean<String>> onIteamClickListener;
    public int style;
    public String[] iteams;
}
