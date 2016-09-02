package com.kaurihealth.kaurihealth.util.Interface;

import android.content.Intent;

/**
 * Created by 张磊 on 2016/6/1.
 * 介绍：
 */
public interface IPickImages {
    void pickImage();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
