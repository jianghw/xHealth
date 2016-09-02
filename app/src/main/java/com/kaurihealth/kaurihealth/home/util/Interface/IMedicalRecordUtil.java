package com.kaurihealth.kaurihealth.home.util.Interface;

import android.view.View;

/**
 * Created by 张磊 on 2016/6/8.
 * 介绍：
 */
public interface IMedicalRecordUtil {
    //设置医疗记录顶部数据，包括(患者姓名，性别，年龄)
    void setTop(View viewTop,String name,String gender,String age);
}
