package com.kaurihealth.kaurihealth.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bugtags.library.Bugtags;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.kaurihealth.util.Interface.IUploadFile;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadServiceBroadcastReceiver;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by 张磊 on 2016/6/1.
 * 介绍：
 */
public class UploadFileUtil implements IUploadFile<String> {
    private SuccessInterfaceM<String> listener;
    private String kauriHealthId;
    private String token;
    private Context activity;
    private String upLoadUrl;
    private MultipartUploadRequest request;
    private final String successDefault = "";
    private UploadServiceBroadcastReceiver receiver = new UploadServiceBroadcastReceiver() {
        @Override
        public void onCompleted(String uploadId, int serverResponseCode, byte[] serverResponseBody) {
            super.onCompleted(uploadId, serverResponseCode, serverResponseBody);
            try {
                    listener.success(new String(serverResponseBody, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                listener.success(successDefault);
            }
        }
        @Override
        public void onError(String uploadId, Exception exception) {
            super.onError(uploadId, exception);
            listener.success(successDefault);
        }

        @Override
        public void onCancelled(String uploadId) {
            super.onCancelled(uploadId);
            listener.success(successDefault);
        }
    };

    public UploadFileUtil(@NonNull String kauriHealthId,
                          @NonNull String token,
                          @NonNull Activity activity,
                          @NonNull String upLoadUrl

    ) {
        this.kauriHealthId = kauriHealthId;
        this.token = token;
        this.upLoadUrl = upLoadUrl;
        this.activity = activity;
        setRequest();
    }


    @Override
    public void startUpload(List<String> paths, SuccessInterfaceM<String> successListener) {
        if (paths == null || paths.size() == 0) {
            listener.success(successDefault);
            return;
        }
        this.listener = successListener;
        for (int i = 0; i < paths.size(); i++) {
            String filePath = paths.get(i);
            String fileName = getFileName(filePath);
            try {
                request.addFileToUpload(filePath, fileName, fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Bugtags.sendException(e);
                listener.success(successDefault);
            }
        }
        try {
            request.startUpload();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            listener.success(successDefault);
        }
    }

    @Override
    public void onResume() {
        receiver.register(activity);
    }

    @Override
    public void onPause() {
        receiver.unregister(activity);
    }

    private void setRequest() {
        MultipartUploadRequest request = new MultipartUploadRequest(activity, upLoadUrl).
                addParameter("kauriHealthId", kauriHealthId).
                setAutoDeleteFilesAfterSuccessfulUpload(false).
                addHeader("Authorization", "Bearer " + token).
                setUsesFixedLengthStreamingMode(false).
                setMaxRetries(4);
        this.request = request;
    }

    private String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return null;
        }
        final String[] filePathParts = filePath.split("/");
        return filePathParts[filePathParts.length - 1];
    }
}
