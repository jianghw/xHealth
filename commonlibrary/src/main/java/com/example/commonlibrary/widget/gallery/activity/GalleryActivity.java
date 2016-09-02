package com.example.commonlibrary.widget.gallery.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.example.commonlibrary.R;
import com.example.commonlibrary.widget.gallery.util.PicassoImageLoader;
import com.example.commonlibrary.widget.util.LogFactory;
import com.example.commonlibrary.widget.util.LogUtilInterface;
import com.veinhorn.scrollgalleryview.MediaInfo;
import com.veinhorn.scrollgalleryview.ScrollGalleryView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张磊 on 2016/5/19.
 * 介绍：
 */
public class GalleryActivity extends FragmentActivity {
    public static final String Data = "Data";
    public static final String PositionKey = "PositionKey";
    LogUtilInterface logUtil = LogFactory.getSimpleLog(getClass().getName());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int position = bundle.getInt(PositionKey);
        ArrayList<String> images = (ArrayList) bundle.getSerializable(Data);
        for (int i = 0; i < images.size(); i++) {
            File file = new File(images.get(i));
            if (file.exists()) {
                images.set(i, Uri.fromFile(file).toString());
            }
        }
        List<MediaInfo> infos = new ArrayList<>(images.size());
        for (String url : images) infos.add(MediaInfo.mediaLoader(new PicassoImageLoader(url)));
        ScrollGalleryView scrollGalleryView = (ScrollGalleryView) findViewById(R.id.scroll_gallery_view);
        scrollGalleryView
                .setThumbnailSize(100)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager())
                .addMedia(infos);
        scrollGalleryView.setCurrentItem(position);
        findViewById(R.id.ivCancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
