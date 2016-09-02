package com.kaurihealth.kaurihealth.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Interface.IPickImages;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;


/**
 * Created by 张磊 on 2016/6/1.
 * 介绍：
 */

/**
 * 建议以后调用
 */
@Deprecated
public class PickImage implements IPickImages {
    private SuccessInterface pathListener;
    private Activity activity;
    private final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private final int REQUEST_IMAGE = 200;
    private ArrayList<String> mSelectPath;
    private int PickImageNum = 100;

    public PickImage(ArrayList<String> mSelectPath, Activity activity, SuccessInterface pathListener) {
        this.activity = activity;
        this.mSelectPath = mSelectPath;
        this.pathListener = pathListener;
    }

    public PickImage(ArrayList<String> mSelectPath, Activity activity, SuccessInterface pathListener, int PickImageNum) {
        this(mSelectPath, activity, pathListener);
        this.PickImageNum = PickImageNum;
    }

    @Override
    public void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, activity.getString(R.string.mis_permission_rationale), REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            MultiImageSelector multiImageSelector = MultiImageSelector.create(activity);
            multiImageSelector.showCamera(true);
            multiImageSelector.count(PickImageNum);
            multiImageSelector.multi();
            multiImageSelector.origin(mSelectPath);
            multiImageSelector.start(activity, REQUEST_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data == null) {
                    return;
                }
                ArrayList<String> stringArrayListExtra = data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT);
                if (stringArrayListExtra == null) {
                    return;
                }
                mSelectPath.clear();
                mSelectPath.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                pathListener.success();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }

    private void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            new AlertDialog.Builder(activity).setTitle(R.string.mis_permission_dialog_title).setMessage(rationale).setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                }
            }).setNegativeButton(R.string.mis_permission_dialog_cancel, null).create().show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
        }
    }


}
