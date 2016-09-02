package com.kaurihealth.kaurihealth.util.Interface;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by 张磊 on 2016/5/30.
 * 介绍：
 */
public interface IStrartActivity {
    void startActivityForResult(Class<? extends Activity> purpose, Bundle bundle, int requestCode);
}
