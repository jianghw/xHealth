package com.kaurihealth.kaurihealth.util.Interface;

import android.app.Activity;
import android.os.Bundle;

/**
 * 版权所有者：刘正
 * 创建日期： 2016/5/27.
 * 备注：
 */
public interface ISkip {
    void skip(Class<? extends Activity> purpose, Bundle bundle,int requestCode);
}
