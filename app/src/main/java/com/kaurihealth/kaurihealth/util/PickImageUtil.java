package com.kaurihealth.kaurihealth.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.widget.BaseAdapter;

import com.example.commonlibrary.widget.util.SuccessInterface;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.util.Abstract.AbsPickImages;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;

/**
 * Created by 张磊 on 2016/6/28.
 * 介绍：
 */
public class PickImageUtil extends AbsPickImages {
    private ArrayList<String> imags;
    private BaseAdapter adapter;
    private SuccessInterface listener;

    public PickImageUtil(Activity activity, ArrayList<String> imags, BaseAdapter adapter) {
        super(activity);
        this.imags = imags;
        this.adapter = adapter;
    }

    public void setListener(SuccessInterface listener) {
        this.listener = listener;
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
                imags.clear();
                imags.addAll(data.getStringArrayListExtra(MultiImageSelector.EXTRA_RESULT));
                adapter.notifyDataSetChanged();
                if (listener!=null) {
                    listener.success();
                }
            }
        }
    }
}
