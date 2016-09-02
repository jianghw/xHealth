package com.kaurihealth.kaurihealth.home.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.BaseAdapter;

import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Interface.IPickImages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by 张磊 on 2016/6/27.
 * 介绍：
 */
public class MedicalRecordPickImage implements IPickImages {
    private Activity activity;
    private List<Object> list;
    private BaseAdapter adapter;
    private int PickImageNum = 100;
    private final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    private final int REQUEST_IMAGE = 200;
    ArrayList<String> imags = new ArrayList<>();

    public MedicalRecordPickImage(Activity activity, List<Object> list, BaseAdapter adapter, int PickImageNum) {
        this.activity = activity;
        this.list = list;
        this.adapter = adapter;
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
            multiImageSelector.origin(imags);
            multiImageSelector.start(activity, REQUEST_IMAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Iterator<Object> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Object next = iterator.next();
                    if (next instanceof String) {
                        iterator.remove();
                    }
                }
                imags.clear();
                imags.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                list.addAll(imags);
                adapter.notifyDataSetChanged();
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
