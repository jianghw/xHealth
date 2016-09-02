package com.kaurihealth.kaurihealth.util.Abstract;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Interface.IPickImages;

/**
 * Created by 张磊 on 2016/6/28.
 * 介绍：
 */
public abstract class AbsPickImages implements IPickImages {
    protected int PickImageNum = 100;
    protected final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected final int REQUEST_IMAGE = 200;
    protected Activity activity;

    public AbsPickImages(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_STORAGE_READ_ACCESS_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        }
    }

    protected void requestPermission(final String permission, String rationale, final int requestCode) {
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
