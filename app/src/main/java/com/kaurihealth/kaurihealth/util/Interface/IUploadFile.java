package com.kaurihealth.kaurihealth.util.Interface;

import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;

import java.util.List;

/**
 * Created by 张磊 on 2016/6/1.
 * 介绍：
 */
public interface IUploadFile<T> {
    void startUpload(List<String> paths, SuccessInterfaceM<T> successListener);

    void onResume();

    void onPause();
}
