package com.kaurihealth.kaurihealth.mine.bean;

import android.content.Context;

import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;

/**
 * Created by 张磊 on 2016/7/12.
 * 介绍：
 */
public class DialogParam {
    public static int ErrorDefault = -100;
    public Context context;
    public String cancleText;
    public String confirmText;
    public SuccessInterfaceM<Integer> cancleOnclickListener;
    public SuccessInterfaceM<Integer> confirmOnclickListener;
    public String[] iteams;
    public int style = ErrorDefault;
}
